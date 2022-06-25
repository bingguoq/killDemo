package com.zzq.killorder.rabbitmq;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


/**
 * rabbitmq配置类
 *
 * @author 224
 * @date 2022/06/24
 */
@Configuration
public class RabbitMQConfig {


    private static final String KiLL_QUEUE_NAME="killQueue";
    private static final String KiLL_EXCHANGE_NAME="killExchange";

    private static final String ORDER_QUEUE_NAME="orderQueue";
    private static final String ORDER_EXCHANGE_NAME="orderExchange";

    private static final String PAY_QUEUE_NAME="payQueue";
    private static final String PAY_EXCHANGE_NAME="payExchange";

    private static final String DEAD_QUEUE_NAME="deadQueue";
    private static final String DEAD_EXCHANGE_NAME="deadExchange";

    @Bean
    public Queue killQueue(){
        return new Queue(KiLL_QUEUE_NAME);
    }

    @Bean
    public DirectExchange killExchange(){
        return new DirectExchange(KiLL_EXCHANGE_NAME);
    }

    @Bean
    public Binding killBinding(){
        return BindingBuilder.bind(killQueue()).to(killExchange()).with("kill");
    }

    @Bean
    public Queue orderQueue(){
        return new Queue(ORDER_QUEUE_NAME);
    }

    @Bean
    public DirectExchange orderExchange(){
        return new DirectExchange(ORDER_EXCHANGE_NAME);
    }

    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order");
    }




    @Bean
    public Queue payQueue(){
        HashMap<String, Object> map = new HashMap<>();
        //设置消息过期时间
        map.put("x-message-ttl",30*1000);
        //绑定死信交换机
        map.put("x-dead-letter-exchange",DEAD_EXCHANGE_NAME);
        //设置路由
        map.put("x-dead-letter-routing-key","dead");
        return new Queue(PAY_QUEUE_NAME,true,false,false,map);
    }

    @Bean
    public DirectExchange payExchange(){
        return new DirectExchange(PAY_EXCHANGE_NAME);
    }

    @Bean
    public Binding payBinding(){
        return BindingBuilder.bind(payQueue()).to(payExchange()).with("pay");
    }


    @Bean
    public Queue deadQueue(){
        return new Queue(DEAD_QUEUE_NAME);
    }

    @Bean
    public DirectExchange deadExchange(){
        return new DirectExchange(DEAD_EXCHANGE_NAME);
    }

    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("dead");
    }

}
