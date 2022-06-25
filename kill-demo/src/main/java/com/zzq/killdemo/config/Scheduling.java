package com.zzq.killdemo.config;

import com.alibaba.fastjson.JSON;
import com.zzq.killdemo.pojo.GoodMessage;
import com.zzq.killdemo.rabbitmq.MQSender;
import com.zzq.killdemo.service.GoodMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * 定时任务类
 *
 * @author 224
 * @date 2022/06/24
 */
@Configuration
@EnableScheduling//开启定时任务
public class Scheduling {

    @Autowired
    GoodMessageService goodMessageService;

    @Autowired
    MQSender mqSender;


    /**
     * 每隔10s查询本地事务表，检查没有被消息的消息并重发消息
     */
    @Scheduled(cron = "0/5 * * * * ? ")
    public void replayMessage(){
        //查询本地事务表
        List<GoodMessage> messages = goodMessageService.selectNoConfirmMessageList();
        //将没有被消费的消息重发
        for (GoodMessage message : messages) {
            mqSender.sendKillMessage(JSON.toJSONString(message));
        }
    }

}
