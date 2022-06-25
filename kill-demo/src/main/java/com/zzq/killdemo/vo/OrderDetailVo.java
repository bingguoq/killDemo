package com.zzq.killdemo.vo;

import com.zzq.killdemo.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {


    private Order order;
    private GoodsVo goodsVo;



}
