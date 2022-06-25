package com.zzq.killorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzq.killorder.pojo.KillGoods;


/**
 * 秒杀货物服务
 *
 * @author 224
 * @date 2022/06/25
 */
public interface KillGoodsService extends IService<KillGoods> {

    /**
     * 减少货存
     *
     * @param id id
     */
    void reduceStock(Long id);


}
