package com.zzq.killorder.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzq.killorder.pojo.Goods;
import com.zzq.killorder.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;


/**
 * 货物Mapper
 *
 * @author 224
 * @date 2022/06/25
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 查询商品通过id
     *
     * @param goodsId 商品id
     * @return {@link GoodsVo}
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
