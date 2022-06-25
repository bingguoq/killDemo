package com.zzq.killdemo.service.impl;

import com.zzq.killdemo.pojo.KillGoods;
import com.zzq.killdemo.mapper.KillGoodsMapper;
import com.zzq.killdemo.service.KillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 秒杀商品服务实现类
 *
 * @author 224
 * @date 2022/06/25
 */
@Service
public class KillGoodsServiceImpl extends ServiceImpl<KillGoodsMapper, KillGoods> implements KillGoodsService {


    @Autowired
    KillGoodsMapper killGoodsMapper;

    /**
     * 减少库存
     *
     * @param goodsId 商品id
     */
    @Override
    public void reduceStock(Long goodsId) {
        killGoodsMapper.reduceStock(goodsId);
    }

}
