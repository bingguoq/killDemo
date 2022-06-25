package com.zzq.killdemo.service;

import com.zzq.killdemo.pojo.KillOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzq.killdemo.pojo.User;


/**
 * 秒杀订单服务
 *
 * @author 224
 * @date 2022/06/25
 */
public interface KillOrderService extends IService<KillOrder> {

    /**
     * 添加添加订单
     *
     * @param killOrder 杀了订单
     */
    void addKillOrder(KillOrder killOrder);

    /**
     * 获取秒杀状态
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return {@link Long}
     */
    Long getResult(User user, Long goodsId);
}
