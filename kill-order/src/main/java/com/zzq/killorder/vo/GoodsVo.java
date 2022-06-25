package com.zzq.killorder.vo;

import com.zzq.killorder.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 货物VO
 *
 * @author 224
 * @date 2022/06/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoodsVo extends Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 秒杀价格 */
    private BigDecimal killPrice;

    /** 库存数量 */
    private Integer stockCount;

    /** 开始日期 */
    private Date startDate;

    /** 结束日期 */
    private Date endDate;


}
