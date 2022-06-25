package com.zzq.killorder.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzq.killorder.mapper.OrderMapper;
import com.zzq.killorder.pojo.*;
import com.zzq.killorder.service.GoodsService;
import com.zzq.killorder.service.KillGoodsService;
import com.zzq.killorder.service.KillOrderService;
import com.zzq.killorder.service.OrderService;
import com.zzq.killorder.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

/**
 * 订单服务实现类
 *
 * @author 224
 * @date 2022/06/25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {



    @Autowired
    OrderMapper orderMapper;

    @Autowired
    GoodsService goodsService;


    @Autowired
    StringRedisTemplate redis;

    @Autowired
    KillGoodsService killGoodsService;

    @Autowired
    KillOrderService killOrderService;


    /**
     * 查询订单通过id
     *
     * @param orderId 订单id
     * @return {@link Order}
     */
    @Override
    public Order selectOrderById(Long orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    /**
     * 生成秒杀订单
     *
     * @param user  用户
     * @param goods 货物
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @Transactional
    @Override
    public HashMap<String,Object> seckill(User user, GoodsVo goods) {

        //减少库存
        killGoodsService.reduceStock(goods.getId());
        //查询秒杀货物信息
        KillGoods killGood=killGoodsService.getOne(new QueryWrapper<KillGoods>()
                .eq("goods_id", goods.getId()));
        //若货存不足则加入redis，以免多次查询
        if(killGood.getStockCount()<1){
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

        //将订单信息放入redis
        redis.opsForValue().set("order:" + user.getId() + ":" + goods.getId(), JSON.toJSONString(killOrder));

        HashMap<String, Object> map = new HashMap<>();
        map.put("killOrderId",killOrder.getId());
        return map;
    }

    /**
     * 删除订单通过id
     *
     * @param orderId 订单id
     */
    @Override
    public void deleteOrderById(Long orderId) {
        orderMapper.deleteOrderById(orderId);
    }


}
