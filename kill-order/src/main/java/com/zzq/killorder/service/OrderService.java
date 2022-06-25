package com.zzq.killorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzq.killorder.pojo.Order;
import com.zzq.killorder.pojo.User;
import com.zzq.killorder.vo.GoodsVo;

import java.util.HashMap;

/**
 * 订单服务
 *
 * @author 224
 * @date 2022/06/25
 */
public interface OrderService extends IService<Order> {

    /**
     * 查询订单通过id
     *
     * @param orderId 订单id
     * @return {@link Order}
     */
    Order selectOrderById(Long orderId);


    /**
     * 生成秒杀订单
     *
     * @param user  用户
     * @param goods 货物
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    HashMap<String,Object> seckill(User user, GoodsVo goods);


    /**
     * 删除订单通过id
     *
     * @param orderId 订单id
     */
    void deleteOrderById(Long orderId);
}
