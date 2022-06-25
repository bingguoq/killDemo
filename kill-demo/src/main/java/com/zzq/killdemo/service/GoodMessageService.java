package com.zzq.killdemo.service;

import com.zzq.killdemo.pojo.GoodMessage;

import java.util.List;

/**
 * 商品消息服务
 *
 * @author 224
 * @date 2022/06/25
 */
public interface GoodMessageService {


     /**
      * 添加消息到本地事务表
      *
      * @param goodMessage 商品消息
      */
     void addMessage(GoodMessage goodMessage);

     /**
      * 更新商品消息状态
      *
      * @param messageId 消息id
      */
     void updateGoodMessage(String messageId);

     /**
      * 查询没有被消费的消息
      *
      * @return {@link List}<{@link GoodMessage}>
      */
     List<GoodMessage> selectNoConfirmMessageList();
}
