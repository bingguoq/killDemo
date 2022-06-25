package com.zzq.killdemo.service;

import com.zzq.killdemo.pojo.KillGoods;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 秒杀商品
 *
 * @author 224
 * @date 2022/06/25
 */
public interface KillGoodsService extends IService<KillGoods> {

    /**
     * 减少库存
     *
     * @param id id
     */
    void reduceStock(Long id);


}
