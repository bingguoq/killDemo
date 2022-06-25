package com.zzq.killorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzq.killorder.pojo.KillOrder;
import com.zzq.killorder.pojo.User;

/**
 * 秒杀订单服务
 *
 * @author 224
 * @date 2022/06/25
 */
public interface KillOrderService extends IService<KillOrder> {

    /**
     * 添加秒杀订单
     *
     * @param killOrder 秒杀订单
     */
    void addKillOrder(KillOrder killOrder);

    /**
     * 得到秒杀状态，-1:秒杀失败,0:排队中
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return {@link Long}
     */
    Long getResult(User user, Long goodsId);

    /**
     * 查询秒杀订单通过id
     *
     * @param id id
     * @return {@link KillOrder}
     */
    KillOrder selectKillOrderById(Long id);

    /**
     * 删除秒杀订单通过id
     *
     * @param id id
     */
    void deleteKillOrderById(Long id);
}
