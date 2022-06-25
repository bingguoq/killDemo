package com.zzq.killdemo.service;

import com.zzq.killdemo.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.vo.GoodsVo;
import com.zzq.killdemo.vo.OrderDetailVo;


/**
 * 订单服务
 *
 * @author 224
 * @date 2022/06/25
 */
public interface OrderService extends IService<Order> {

    /**
     * 订单生成
     *
     * @param user  用户
     * @param goods 货物
     * @return {@link Order}
     */
    Order seckill(User user, GoodsVo goods);

    /**
     * 订单详情
     *
     * @param orderId 订单id
     * @return {@link OrderDetailVo}
     */
    OrderDetailVo detail(Long orderId);

    /**
     * 创建秒杀路径
     *
     * @param user    用户
     * @param goodsId 商品id
     * @return {@link String}
     */
    String createPath(User user, Long goodsId);

    /**
     * 检查路径
     *
     * @param user    用户
     * @param goodsId 商品id
     * @param path    路径
     * @return boolean
     */
    boolean checkPath(User user,Long goodsId, String path);

    /**
     * 检查验证码
     *
     * @param user    用户
     * @param goodsId 商品id
     * @param captcha 验证码
     * @return boolean
     */
    boolean checkCaptcha(User user, Long goodsId, String captcha);
}
