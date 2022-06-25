package com.zzq.killdemo.mapper;

import com.zzq.killdemo.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzq.killdemo.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 货物Mapper
 *
 * @author 224
 * @date 2022/06/25
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 查询商品
     *
     * @return {@link List}<{@link GoodsVo}>
     */
    List<GoodsVo> findGoodVo();

    /**
     * 查询商品通过商品id
     *
     * @param goodsId 商品id
     * @return {@link GoodsVo}
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
