package com.zzq.killdemo.mapper;

import com.zzq.killdemo.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 订单Mapper
 *
 * @author 224
 * @date 2022/06/25
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {


    /**
     * 添加订单
     *
     * @param order 订单
     * @return int
     */
    int addOrder(Order order);

    /**
     * 查询订单通过订单id
     *
     * @param orderId 订单id
     * @return {@link Order}
     */
    Order selectOrderById(Long orderId);
}
