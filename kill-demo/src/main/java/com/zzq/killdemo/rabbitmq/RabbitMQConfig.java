package com.zzq.killdemo.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

//    @Bean
//    public Binding orderBinding(){
//        return BindingBuilder.bind(killQueue()).to(killExchange()).with("order");
//    }
    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order");
    }
}
