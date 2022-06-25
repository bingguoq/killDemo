package com.zzq.killdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzq.killdemo.exception.GlobalException;
import com.zzq.killdemo.pojo.KillGoods;
import com.zzq.killdemo.pojo.KillOrder;
import com.zzq.killdemo.pojo.Order;
import com.zzq.killdemo.mapper.OrderMapper;
import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.service.GoodsService;
import com.zzq.killdemo.service.KillGoodsService;
import com.zzq.killdemo.service.KillOrderService;
import com.zzq.killdemo.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzq.killdemo.utils.MD5Util;
import com.zzq.killdemo.vo.GoodsVo;
import com.zzq.killdemo.vo.OrderDetailVo;
import com.zzq.killdemo.vo.RespBeanEnum;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 订单服务实现类
 *
 * @author 224
 * @date 2022/06/25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private KillGoodsService killGoodsService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    GoodsService goodsService;

    @Autowired
    KillOrderService killOrderService;

    @Autowired
    StringRedisTemplate redis;


    /**
     * 订单生成
     *
     * @param user  用户
     * @param goods 货物
     * @return {@link Order}
     */
    @Transactional
    @Override
    public Order seckill(User user, GoodsVo goods) {

        //减少库存
        killGoodsService.reduceStock(goods.getId());
        //获取秒杀货物的信息
        KillGoods killGood=killGoodsService.getOne(new QueryWrapper<KillGoods>()
                .eq("goods_id", goods.getId()));
        //若库存不足
        if(killGood.getStockCount()<1){
            //将库存不足的信息放入redis，防止此后再次查询数据库
            redis.opsForValue().set("isStockEmpty:"+goods.getId(),"0");
        }

        //订单生成
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(killGood.getKillPrice());
        order.setStatus(0);
        order.setOrderChannel(1);
        order.setCreateDate(new Date());
        orderMapper.addOrder(order);

        //秒杀订单生成
        KillOrder killOrder = new KillOrder();
        killOrder.setUserId(user.getId());
        killOrder.setOrderId(order.getId());
        killOrder.setGoodsId(goods.getId());
        killOrderService.addKillOrder(killOrder);

        //订单信息放入redis
        redis.opsForValue().set("order:" + user.getId() + ":" + goods.getId(), JSON.toJSONString(killOrder));
        return order;
    }


    /**
     * 查询订单详细信息
     *
     * @param orderId 订单id
     * @return {@link OrderDetailVo}
     */
    @Override
    public OrderDetailVo detail(Long orderId) {
        //如果订单id为空则抛出异常
        if(orderId==null){
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }

        //根据订单id查询订单信息
        Order order = orderMapper.selectOrderById(orderId);
        //根据商品id获取商品的信息
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(order.getGoodsId());

        //将订单信息和商品信息进行封装
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(order);
        orderDetailVo.setGoodsVo(goodsVo);

        return orderDetailVo;
    }


    /**
     * 创建秒杀路径
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return {@link String}
     */
    @Override
    public String createPath(User user, Long goodsId) {
        //随机生成路径，将其放入redis中，设置过期时间为60s
        String s = MD5Util.md5(UUID.randomUUID().toString());
        redis.opsForValue().set("secKillPath:"+user.getId()+":"+goodsId,s,60, TimeUnit.SECONDS);
        return s;
    }

    /**
     * 检查m秒杀路径
     *
     * @param user    用户
     * @param goodsId 商品id
     * @param path    秒杀路径
     * @return boolean
     */
    @Override
    public boolean checkPath(User user,Long goodsId, String path) {
        //从redis中获取获取之前生成路径进行校验
        String redisPath = redis.opsForValue().get("secKillPath:" + user.getId() + ":" + goodsId);
        return redisPath.equals(path);
    }

    /**
     * 检查验证码
     *
     * @param user    用户
     * @param goodsId 商品id
     * @param captcha 验证码
     * @return boolean
     */
    @Override
    public boolean checkCaptcha(User user, Long goodsId, String captcha) {
        //若验证码为空或者商品ID不存在则验证失败，返回false
        if(StringUtil.isNullOrEmpty(captcha)||goodsId<0){
            return false;
        }
        //从redis中获取验证码的结果
        String s = redis.opsForValue().get("captcha:" + user.getId() + ":" + goodsId);
        //判断验证码是否正确
        return captcha.equals(s);
    }
}
