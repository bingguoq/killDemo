package com.zzq.killdemo.vo;

import com.zzq.killdemo.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetailVo {


    private User user;
    private GoodsVo goodsVo;

    private int secKillStatus;
    private int remainSeconds;
}
