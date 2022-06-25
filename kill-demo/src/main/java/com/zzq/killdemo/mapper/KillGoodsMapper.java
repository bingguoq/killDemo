package com.zzq.killdemo.mapper;

import com.zzq.killdemo.pojo.KillGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 秒杀商品Mapper
 *
 * @author 224
 * @date 2022/06/25
 */
@Mapper
public interface KillGoodsMapper extends BaseMapper<KillGoods> {

    /**
     * 减少库存
     *
     * @param goodsId 商品id
     */
    void reduceStock(Long goodsId);



}
