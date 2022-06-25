package com.zzq.killdemo.controller;


import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.service.OrderService;
import com.zzq.killdemo.vo.OrderDetailVo;
import com.zzq.killdemo.vo.RespBean;
import com.zzq.killdemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 订单控制器
 *
 * @author 224
 * @date 2022/06/24
 */
@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    /**
     * 订单详细信息
     *
     * @param user    用户
     * @param orderId 订单id
     * @return {@link RespBean}
     */
    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user,Long orderId){
        //判断用户是否登录
        if(user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        //根据订单id查询订单信息
        OrderDetailVo detail=orderService.detail(orderId);
        return RespBean.success(detail);
    }
}
