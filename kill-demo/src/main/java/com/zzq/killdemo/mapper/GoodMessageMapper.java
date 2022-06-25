package com.zzq.killdemo.mapper;

import com.zzq.killdemo.pojo.GoodMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品消息Mapper
 *
 * @author 224
 * @date 2022/06/25
 */
@Mapper
public interface GoodMessageMapper {

    /**
     * 更新商品状态为已被消费
     *
     * @param messageId 消息id
     */
    void updateGoodMessage(String messageId);

    /**
     * 添加消息
     *
     * @param goodMessage 商品消息
     */
    void addMessage(@Param("goodMessage") GoodMessage goodMessage);

    /**
     * 查询没有被消费的消息
     *
     * @return {@link List}<{@link GoodMessage}>
     */
    List<GoodMessage> selectNoConfirmMessageList();
}
