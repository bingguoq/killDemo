package com.zzq.killorder.service;


import com.zzq.killorder.pojo.OrderMessage;

/**
 * 订单信息服务
 *
 * @author 224
 * @date 2022/06/25
 */
public interface OrderMessageService {

    /**
     * 查询某个状态的订单消息数
     *
     * @param messageId 消息id
     * @return int
     */
    int selectOrderMessageCount(String messageId);

    /**
     * 添加订单消息
     *
     * @param orderMessage 订单消息
     */
    void addOrderMessage(OrderMessage orderMessage);



}
