package com.zzq.killorder.rabbitmq;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * rabbitmq消息发送类
 *
 * @author 224
 * @date 2022/06/24
 */
@Component
public class MQSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 向秒杀队列发送消息
     *
     * @param message 消息
     */
    public void sendKillMessage(String message){
        rabbitTemplate.convertAndSend("orderExchange","order",message);
    }

    /**
     * 向支付队列发送消息
     *
     * @param message 消息
     */
    public void sendPayQueue(String message){
        rabbitTemplate.convertAndSend("payExchange","pay",message);
    }


}
