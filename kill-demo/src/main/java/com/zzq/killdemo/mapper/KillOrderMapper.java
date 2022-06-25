package com.zzq.killdemo.mapper;

import com.zzq.killdemo.pojo.KillOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * 秒杀订单Mapper
 *
 * @author 224
 * @date 2022/06/25
 */
@Mapper
public interface KillOrderMapper extends BaseMapper<KillOrder> {

    /**
     * 添加秒杀订单
     *
     * @param killOrder 杀了订单
     */
    void addKillOrder(KillOrder killOrder);

    /**
     * 查询秒杀订单
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return {@link KillOrder}
     */
    KillOrder selectKillOrder(@Param("userId") Long userId, @Param("goodsId")Long goodsId);
}
