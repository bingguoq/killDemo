package com.zzq.killdemo.controller;


import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.service.GoodsService;
import com.zzq.killdemo.vo.DetailVo;
import com.zzq.killdemo.vo.GoodsVo;
import com.zzq.killdemo.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;


/**
 * 产品控制器
 *
 * @author 224
 * @date 2022/06/24
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 返回货物列表
     *
     * @param model 模型
     * @param user  用户
     * @return {@link String}
     */
    @RequestMapping("/toList")
    public String toList(Model model, User user) {
        model.addAttribute("user",user);
        //查询货物基本信息
        model.addAttribute("goodsList", goodsService.findGoodVo());
        return "goodsList";
    }

    /**
     * 返回货物详细信息
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return {@link RespBean}
     */
    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(User user,@PathVariable Long goodsId) {

        //通过货物ID获取其货物及其秒杀信息
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);

        //获取商品秒杀的开始时间和结束时间
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();

        Date nowDate = new Date();//当前时间

        int secKillStatus=0;//秒杀状态，0-未开始，1-正在进行，2-已结束

        int remainSeconds=0;//距离秒杀开始的剩余时间，单位为秒，

        //秒杀还没有开始
        if(nowDate.before(startDate)){
            //距离秒杀开始的剩余时间
            remainSeconds= (int) ((startDate.getTime()-nowDate.getTime())/1000);
        }else if(nowDate.after(endDate)){//秒杀已经结束
            secKillStatus=2;
            remainSeconds=-1;
        }else {//秒杀正在进行
            secKillStatus=1;
            remainSeconds=0;
        }

        //封装秒杀商品的详细信息并返回
        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setSecKillStatus(secKillStatus);
        detailVo.setRemainSeconds(remainSeconds);
        return RespBean.success(detailVo);
    }
}
