package com.zzq.killorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzq.killorder.pojo.KillGoods;
import org.apache.ibatis.annotations.Mapper;


/**
 * 秒杀货物Mapper
 *
 * @author 224
 * @date 2022/06/25
 */
@Mapper
public interface KillGoodsMapper extends BaseMapper<KillGoods> {

    /**
     * 减少货存
     *
     * @param goodsId 商品id
     */
    void reduceStock(Long goodsId);



}
