package com.zzq.killdemo.rabbitmq;


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
        rabbitTemplate.convertAndSend("killExchange","kill",message);
    }
}
