package com.zzq.killorder;

import com.alibaba.fastjson.JSON;
import com.zzq.killorder.pojo.KillOrder;
import com.zzq.killorder.pojo.OrderMessage;
import com.zzq.killorder.rabbitmq.MQSender;
import com.zzq.killorder.service.GoodsService;
import com.zzq.killorder.service.KillOrderService;
import com.zzq.killorder.service.OrderMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KillOrderApplicationTests {

  @Autowired
  OrderMessageService orderMessageService;

  @Autowired
  MQSender mqSender;


  @Autowired
  KillOrderService killOrderService;
  //  public void sendKillMessage(String message){
  //        rabbitTemplate.convertAndSend("orderExchange","order",message);
  //    }

}
