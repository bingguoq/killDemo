package com.zzq.killdemo.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 商品消息
 *
 * @author 224
 * @date 2022/06/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoodMessage {

    /** 消息id */
    private String messageId;

    /** 用户id */
    private Long userId;

    /** 商品id */
    private Long goodId;

    /** 消息状态 */
    private int messageStatus;

}
