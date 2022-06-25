package com.zzq.killorder.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzq.killorder.pojo.KillOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


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
     * @param killOrder 秒杀订单
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
