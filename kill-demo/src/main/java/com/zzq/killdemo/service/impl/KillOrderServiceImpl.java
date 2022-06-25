package com.zzq.killdemo.service.impl;

import com.zzq.killdemo.pojo.KillOrder;
import com.zzq.killdemo.mapper.KillOrderMapper;
import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.service.KillOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


/**
 * 秒杀订单服务实现鸟
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
     * @param killOrder 杀了订单
     */
    @Override
    public void addKillOrder(KillOrder killOrder) {
        killOrderMapper.addKillOrder(killOrder);
    }


    /**
     * 获取秒杀结果，-1:秒杀失败,0:排队中
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return {@link Long}
     */
    @Override
    public Long getResult(User user, Long goodsId) {
        //查询订单是否存在
        KillOrder killOrder=killOrderMapper.selectKillOrder(user.getId(),goodsId);
        //若存在则返回订单状态信息
        if(killOrder!=null) {
            return killOrder.getOrderId();
        }else if(redis.hasKey("isStockEmpty:"+goodsId)){//若订单不存在且reids中对应商品的id为空，则商品已经被秒杀完了
            return -1L;
        }else{//排队秒杀中
            return 0L;
        }
    }
}









