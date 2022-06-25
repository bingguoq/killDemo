package com.zzq.killorder.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzq.killorder.mapper.GoodsMapper;
import com.zzq.killorder.pojo.Goods;
import com.zzq.killorder.service.GoodsService;
import com.zzq.killorder.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品服务impl
 *
 * @author 224
 * @date 2022/06/25
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {


    @Autowired
    private GoodsMapper goodsMapper;


    /**
     * 查询货物信息通过id
     *
     * @param goodsId 商品id
     * @return {@link GoodsVo}
     */
    @Override
    public GoodsVo findGoodsVoByGoodsId(Long goodsId) {
        return goodsMapper.findGoodsVoByGoodsId(goodsId);
    }
}
