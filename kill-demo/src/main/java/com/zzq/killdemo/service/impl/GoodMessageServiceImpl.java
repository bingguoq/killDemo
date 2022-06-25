package com.zzq.killdemo.service.impl;

import com.zzq.killdemo.mapper.GoodMessageMapper;
import com.zzq.killdemo.pojo.GoodMessage;
import com.zzq.killdemo.service.GoodMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品消息服务实现类
 *
 * @author 224
 * @date 2022/06/25
 */
@Service
public class GoodMessageServiceImpl implements GoodMessageService {

    @Autowired
    GoodMessageMapper goodMessageMapper;

    /**
     * 更新商品状态
     *
     * @param messageId 消息id
     */
    @Override
    public void updateGoodMessage(String messageId) {
        goodMessageMapper.updateGoodMessage(messageId);
    }

    /**
     * 查询没有被消费的消息
     *
     * @return {@link List}<{@link GoodMessage}>
     */
    @Override
    public List<GoodMessage> selectNoConfirmMessageList() {
        return goodMessageMapper.selectNoConfirmMessageList();
    }

    /**
     * 添加消息
     *
     * @param goodMessage 商品消息
     */
    @Override
    public void addMessage(GoodMessage goodMessage) {
        goodMessageMapper.addMessage(goodMessage);
    }
}
