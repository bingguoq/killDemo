package com.zzq.killorder.mapper;

import com.zzq.killorder.pojo.OrderMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单消息Mapper
 *
 * @author 224
 * @date 2022/06/25
 */
@Mapper
public interface OrderMessageMapper {
    /**
     * 查询某个状态的订单消息数
     *
     * @param messageId 消息id
     * @return int
     */
    int selectOrderMessageCount(String messageId);

    /**
     * 添加订单消息
     *
     * @param orderMessage 订单消息
     */
    void addOrderMessage(@Param("orderMessage") OrderMessage orderMessage);


}
