package com.zzq.killdemo.service.impl;

import com.zzq.killdemo.pojo.Goods;
import com.zzq.killdemo.mapper.GoodsMapper;
import com.zzq.killdemo.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzq.killdemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 商品服务实现
 *
 * @author 224
 * @date 2022/06/24
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {


    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 获取其货物及其秒杀信息
     *
     * @return {@link List}<{@link GoodsVo}>
     */
    @Override
    public List<GoodsVo> findGoodVo() {
        return goodsMapper.findGoodVo();
    }

    /**
     * 通过货物ID获取其货物及其秒杀信息
     *
     * @param goodsId 商品id
     * @return {@link GoodsVo}
     */
    @Override
    public GoodsVo findGoodsVoByGoodsId(Long goodsId) {
        return goodsMapper.findGoodsVoByGoodsId(goodsId);
    }
}
