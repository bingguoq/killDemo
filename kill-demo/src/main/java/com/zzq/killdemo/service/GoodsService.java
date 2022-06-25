package com.zzq.killdemo.service;

import com.zzq.killdemo.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzq.killdemo.vo.GoodsVo;

import java.util.List;


/**
 * 商品服务
 *
 * @author 224
 * @date 2022/06/25
 */
public interface GoodsService extends IService<Goods> {


    /**
     * 获取其货物及其秒杀信息
     *
     * @return {@link List}<{@link GoodsVo}>
     */
    List<GoodsVo> findGoodVo();

    /**
     * 通过货物ID获取其货物及其秒杀信息
     *
     * @param goodsId 商品id
     * @return {@link GoodsVo}
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
