package com.zzq.killorder.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 秒杀消息
 *
 * @author 224
 * @date 2022/06/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KillMessage {


    /** 用户信息 */
    private User user;
    /** 商品id */
    private Long goodId;


}
