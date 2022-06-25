package com.zzq.killorder.service.Impl;

import com.zzq.killorder.mapper.OrderMessageMapper;
import com.zzq.killorder.pojo.OrderMessage;
import com.zzq.killorder.service.OrderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单消息服务实现类
 *
 * @author 224
 * @date 2022/06/25
 */
@Service
public class OrderMessageServiceImpl implements OrderMessageService {

    @Autowired
    OrderMessageMapper orderMessageMapper;


    /**
     * 查询某个状态的订单消息数
     *
     * @param messageId 消息id
     * @return int
     */
    @Override
    public int selectOrderMessageCount(String messageId) {
        return orderMessageMapper.selectOrderMessageCount(messageId);
    }

    /**
     * 添加订单消息
     *
     * @param orderMessage 订单消息
     */
    @Override
    public void addOrderMessage(OrderMessage orderMessage) {
        orderMessageMapper.addOrderMessage(orderMessage);
    }
}
