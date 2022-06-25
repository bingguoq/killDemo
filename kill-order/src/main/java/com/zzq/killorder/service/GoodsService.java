package com.zzq.killorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzq.killorder.pojo.Goods;
import com.zzq.killorder.vo.GoodsVo;


/**
 * 商品服务
 *
 * @author 224
 * @date 2022/06/25
 */
public interface GoodsService extends IService<Goods> {


    /**
     * 查询货物通过货物id
     *
     * @param goodsId 商品id
     * @return {@link GoodsVo}
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
