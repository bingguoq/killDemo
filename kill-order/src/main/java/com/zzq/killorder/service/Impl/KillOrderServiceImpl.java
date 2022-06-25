package com.zzq.killorder.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzq.killorder.mapper.KillOrderMapper;
import com.zzq.killorder.pojo.KillOrder;
import com.zzq.killorder.pojo.User;
import com.zzq.killorder.service.KillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


/**
 * 秒杀订单服务实现类
 *
 * @author 224
 * @date 2022/06/25
 */
@Service
public class KillOrderServiceImpl extends ServiceImpl<KillOrderMapper, KillOrder> implements KillOrderService {

    @Autowired
    KillOrderMapper killOrderMapper;

    @Autowired
    StringRedisTemplate redis;

    /**
     * 添加秒杀订单
     *
     * @param killOrder 秒杀订单
     */
    @Override
    public void addKillOrder(KillOrder killOrder) {
        killOrderMapper.addKillOrder(killOrder);
    }


    /**
     * 获取秒杀状态，-1:秒杀失败,0:排队中
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return {@link Long}
     */
    @Override
    public Long getResult(User user, Long goodsId) {
        //查询秒杀订单
        KillOrder killOrder=killOrderMapper.selectKillOrder(user.getId(),goodsId);
        if(killOrder!=null) {
            return killOrder.getOrderId();
        }else if(redis.hasKey("isStockEmpty:"+goodsId)){
            return -1L;
        }else{
            return 0L;
        }
    }

    /**
     * 查询秒杀订单通过id
     *
     * @param id id
     * @return {@link KillOrder}
     */
    @Override
    public KillOrder selectKillOrderById(Long id) {
        return killOrderMapper.selectKillOrderById(id);

    }

    /**
     * 删除秒杀订单通过id
     *
     * @param id id
     */
    @Override
    public void deleteKillOrderById(Long id) {
        killOrderMapper.deleteKillOrderById(id);
    }
}









