package com.zzq.killdemo.vo;

import com.zzq.killdemo.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoodsVo extends Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal killPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;


}
