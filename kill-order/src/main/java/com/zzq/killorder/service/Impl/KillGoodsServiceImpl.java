package com.zzq.killorder.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzq.killorder.mapper.KillGoodsMapper;
import com.zzq.killorder.pojo.KillGoods;
import com.zzq.killorder.service.KillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 秒杀货物服务实现类
 *
 * @author 224
 * @date 2022/06/25
 */
@Service
public class KillGoodsServiceImpl extends ServiceImpl<KillGoodsMapper, KillGoods> implements KillGoodsService {


    @Autowired
    KillGoodsMapper killGoodsMapper;

    /**
     * 减少货存
     *
     * @param goodsId 商品id
     */
    @Override
    public void reduceStock(Long goodsId) {
        killGoodsMapper.reduceStock(goodsId);
    }

}
