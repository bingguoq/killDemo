package com.zzq.killdemo.controller;


import com.alibaba.fastjson.JSON;
import com.wf.captcha.ArithmeticCaptcha;
import com.zzq.killdemo.annoation.AccessLimit;
import com.zzq.killdemo.exception.GlobalException;
import com.zzq.killdemo.pojo.GoodMessage;
import com.zzq.killdemo.pojo.KillMessage;
import com.zzq.killdemo.pojo.KillOrder;
import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.rabbitmq.MQSender;
import com.zzq.killdemo.service.GoodMessageService;
import com.zzq.killdemo.service.GoodsService;
import com.zzq.killdemo.service.KillOrderService;
import com.zzq.killdemo.service.OrderService;
import com.zzq.killdemo.vo.GoodsVo;
import com.zzq.killdemo.vo.RespBean;
import com.zzq.killdemo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀货物Controller
 *
 * @author 224
 * @date 2022/06/24
 */
@Slf4j
@Controller
@RequestMapping("/secKill")
public class KillGoodsController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private KillOrderService killOrderService;

    @Autowired
    OrderService orderService;

    @Autowired
    MQSender mqSender;

    @Autowired
    StringRedisTemplate redis;

    @Autowired
    GoodMessageService goodMessageService;


    //判断秒杀商品库存是否剩余，避免重复查询BD
    private Map<Long,Boolean> EmptyStockMap=new HashMap<>();


    /**
     * 获取秒杀路径
     *
     * @param user    用户
     * @param goodsId 商品id
     * @param captcha 验证码
     * @return {@link RespBean}
     */
    @AccessLimit(second = 5,maxCount = 5)
    @RequestMapping("/path")
    @ResponseBody
    public RespBean getPath(User user,Long goodsId,String captcha){
        //判断用户是否登录
        if(user==null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        //校验验证码
        boolean check=orderService.checkCaptcha(user,goodsId,captcha);
        //若校验失败则返回验证码错误
        if(!check){
            return RespBean.error(RespBeanEnum.ERROR_CAPTCHA);
        }
        //生成秒杀路径
        String str=orderService.createPath(user,goodsId);
        return RespBean.success(str);
    }


    /**
     * 做秒杀
     *
     * @param path    秒杀路径
     * @param user    用户
     * @param goodsId 商品id
     * @return {@link RespBean}
     */
    @RequestMapping(value = "{path}/doSecKill",method = RequestMethod.POST)
    @ResponseBody
    public RespBean doSecKill(@PathVariable String path, User user, Long goodsId){
        //判断用户是否登录
        if(user==null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }

        //检查秒杀路径
        boolean check=orderService.checkPath(user,goodsId,path);
        //若路径错误则返回请求错误
        if(!check){
            return RespBean.error(RespBeanEnum.REQUEST_ILLEGAL);
        }

        //判断该用户是否已经购买过（每人限购一件）
        //查询是否存在对应的订单信息
        String s = redis.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        KillOrder killOrder = JSON.toJavaObject(JSON.parseObject(s), KillOrder.class);
        if(killOrder!=null){
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }

        //判断当前货物是否为空
        if(EmptyStockMap.get(goodsId)){
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        //redis预减库存
        Long stock = redis.opsForValue().decrement("secKillGoods:" + goodsId);

        //货存不够
        if(stock<0){
            EmptyStockMap.put(goodsId,true);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        //生成订单
        GoodMessage goodMessage=new GoodMessage(UUID.randomUUID().toString().substring(0,8),
                user.getId(),goodsId, 0);
        //添加到订单信息表
        goodMessageService.addMessage(goodMessage);

        //KillMessage killMessage = new KillMessage(user, goodsId);

        //秒杀订单生成，使用rabbitmq进行异步订单生成
        mqSender.sendKillMessage(JSON.toJSONString(goodMessage));

        return RespBean.success();
    }


    /**
     * 获取当前秒杀的状态 -1:秒杀失败,0:排队中，成功则返回货物ID
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return {@link RespBean}
     */
    @RequestMapping(value = "/getResult",method = RequestMethod.GET)
    @ResponseBody
    public RespBean getResult(User user,Long goodsId){
        //判断用户是否登录
        if(user==null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        //获取秒杀的结果
        Long orderId=killOrderService.getResult(user,goodsId);
        return RespBean.success(orderId);
    }


    /**
     * 生成验证码
     *
     * @param user     用户
     * @param goodsId  商品id
     * @param response 响应
     */
    @RequestMapping("/captcha")
    private void verifyCode(User user, Long goodsId, HttpServletResponse response){
        //判断用户是否登录
        if(user==null) {
            throw new GlobalException(RespBeanEnum.REQUEST_ILLEGAL);
        }

        //生成验证码
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 32, 3);

        //将验证码答案放入redis中
        redis.opsForValue().set("captcha:"+user.getId()+":"+goodsId, captcha.text(),300, TimeUnit.SECONDS);

        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
           log.error("验证码成功失败",e.getMessage());
        }
    }


    /**
     * bean初始化方法，提前将货物储存信息存入redis和map中
     *
     * @throws Exception 异常
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodVo = goodsService.findGoodVo();
        if(CollectionUtils.isEmpty(goodVo)) return;
        for (GoodsVo goodsVo : goodVo) {
            EmptyStockMap.put(goodsVo.getId(),false);
            redis.opsForValue().set("secKillGoods:"+goodsVo.getId(),goodsVo.getStockCount().toString());
        }
    }


    /**
     * 秒杀接口测试
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return {@link RespBean}
     */
    @RequestMapping(value = "TestSecKill",method = RequestMethod.POST)
    @ResponseBody
    public RespBean testkill(User user, Long goodsId){
        if(user==null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }

        //判断该用户是否已经购买过（每人限购一件）
        String s = redis.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        KillOrder killOrder = JSON.toJavaObject(JSON.parseObject(s), KillOrder.class);
        if(killOrder!=null){
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }

        //查询当前货物是否为空
        if(EmptyStockMap.get(goodsId)){
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        Long stock = redis.opsForValue().decrement("secKillGoods:" + goodsId);
        if(stock<0){
            EmptyStockMap.put(goodsId,true);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        //生成订单
        KillMessage killMessage = new KillMessage(user, goodsId);
        mqSender.sendKillMessage(JSON.toJSONString(killMessage));
        return RespBean.success(0);
    }

}
