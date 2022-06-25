package com.zzq.killorder.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.zzq.killorder.pojo.*;

import com.zzq.killorder.service.GoodsService;
import com.zzq.killorder.service.KillOrderService;
import com.zzq.killorder.service.OrderMessageService;
import com.zzq.killorder.service.OrderService;
import com.zzq.killorder.vo.GoodsVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * rabbitmq接受消息类
 *
 * @author 224
 * @date 2022/06/24
 */
@Component
public class MQReceiver {

    @Autowired
    GoodsService goodsService;

    @Autowired
    StringRedisTemplate redis;

    @Autowired
    MQSender mqSender;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageService orderMessageService;

    @Autowired
    KillOrderService killOrderService;


    /**
     * 监听秒杀队列，负责生成秒杀订单
     *
     * @param message 消息
     */
    @RabbitListener(queues = "killQueue")
    public void orderReceive(String message){

        GoodMessage goodMessage = JSON.toJavaObject(JSON.parseObject(message), GoodMessage.class);

        //通过消息id查询该消息是否已经被消费过了
        int i = orderMessageService.selectOrderMessageCount(goodMessage.getMessageId());
        //如果已经被消费了，则将消息发送给订单队列，让其修改消息的id为已消息状态
        if(i>0) {
            mqSender.sendKillMessage(JSON.toJSONString(goodMessage));
            return;
        }

        //使用redis加上分布式锁
        String uuid = UUID.randomUUID().toString();
        Boolean ok = redis.opsForValue().setIfAbsent("LOCK:killGoods", uuid, 2L, TimeUnit.SECONDS);
        if(ok){
            //判断是否买过（二次检查，一人只能买一次）
            String s = redis.opsForValue().get("order:" + goodMessage.getUserId() + ":" + goodMessage.getGoodId());
            KillOrder killOrder = JSON.toJavaObject(JSON.parseObject(s), KillOrder.class);
            //若买过则无视该请求
            if(killOrder!=null){
                return;
            }

            //通过商品id查询货物信息
            GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodMessage.getGoodId());
            //判断库存容量
            if(goodsVo.getStockCount()<1){
                return;
            }

            //将订单部分信息放入redis作为缓存，以便检查用户是否已经买过了
            redis.opsForValue().set("order:" + goodMessage.getUserId() + ":" + goodMessage.getGoodId(),"1");
            //生成订单
            HashMap<String, Object> map = orderService.seckill(new User(goodMessage.getUserId()), goodsVo);

            OrderMessage orderMessage = new OrderMessage(goodMessage.getMessageId(), goodMessage.getUserId(),
                    goodMessage.getGoodId(), 1);
            //订单生成后，将订单信息插入插入事务表
            orderMessageService.addOrderMessage(orderMessage);

            //将秒杀订单的信息发送给支付队列进行处理
            Long killOrderId = (Long) map.get("killOrderId");
            mqSender.sendPayQueue(killOrderId.toString());

            //使用lua脚本原子性删除锁
            String lock = redis.opsForValue().get("LOCK:killGoods");
            String script="if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            redis.execute(new DefaultRedisScript<>(script,Long.class),
                    Arrays.asList("LOCK:killGoods"),uuid);
        }
        //插入本地事务表
        mqSender.sendKillMessage(JSON.toJSONString(goodMessage));
    }


    /**
     * 监听死信队列
     */
    @RabbitListener(queues = "deadQueue")
    public void payReceive(String message){
        //若订单在规定时间内没有被消费者消费则会进入死信队列，

        //反序列化获取需要需要的订单的订单id
        Long id = Long.valueOf(message);

        //根据id查询秒杀订单信息
        KillOrder killOrder=killOrderService.selectKillOrderById(id);

        Long orderId = killOrder.getOrderId();
        //根据id查询的订单信息
        Order order=orderService.selectOrderById(orderId);
        //订单未支付
        if(order.getStatus()==0){
            //删除订单
            killOrderService.deleteKillOrderById(id);
            orderService.deleteOrderById(orderId);
        }
    }
}
