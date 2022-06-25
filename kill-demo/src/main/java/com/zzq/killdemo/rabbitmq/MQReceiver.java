package com.zzq.killdemo.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzq.killdemo.pojo.GoodMessage;
import com.zzq.killdemo.pojo.KillMessage;
import com.zzq.killdemo.pojo.KillOrder;
import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.service.GoodMessageService;
import com.zzq.killdemo.service.GoodsService;
import com.zzq.killdemo.service.OrderService;
import com.zzq.killdemo.vo.GoodsVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

/**
 * rabbitmq消息接收类
 *
 * @author 224
 * @date 2022/06/24
 */
@Component
public class MQReceiver {

    @Autowired
    GoodMessageService goodMessageService;

    /**
     * 监听订单队列
     *
     * @param message 消息
     */
    @RabbitListener(queues = "orderQueue")
    public void receive(String message){
        //修改本地事务表，将消息修改为以被消费状态
        JSONObject jsonObject = JSON.parseObject(message);
        GoodMessage goodMessage = JSON.toJavaObject(jsonObject, GoodMessage.class);
        goodMessageService.updateGoodMessage(goodMessage.getMessageId());
    }






//    @Autowired
//    GoodsService goodsService;
//
//    @Autowired
//    StringRedisTemplate redis;
//
//    @Autowired
//    OrderService orderService;
//
//
//    @RabbitListener(queues = "killQueue")
//    public void receive(String message){
//
//        KillMessage killMessage = JSON.toJavaObject(JSON.parseObject(message), KillMessage.class);
//        User user = killMessage.getUser();
//
//
//        String uuid = UUID.randomUUID().toString();
//        Boolean ok = redis.opsForValue().setIfAbsent("LOCK:killGoods", uuid, 2L, TimeUnit.SECONDS);
//        if(ok){
//            //判断是否买过
//            String s = redis.opsForValue().get("order:" + user.getId() + ":" + killMessage.getGoodId());
//            KillOrder killOrder = JSON.toJavaObject(JSON.parseObject(s), KillOrder.class);
//            if(killOrder!=null){
//                return;
//            }
//
//            //查询货物信息
//            GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(killMessage.getGoodId());
//            //判断库存容量
//            if(goodsVo.getStockCount()<1){
//                return;
//            }
//
//            //生成订单
//            orderService.seckill(user,goodsVo);
//
//            //删锁
//            String lock = redis.opsForValue().get("LOCK:killGoods");
//            String script="if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
//            redis.execute(new DefaultRedisScript<>(script,Long.class),
//                    Arrays.asList("LOCK:killGoods"),uuid);
//            return;
//        }
//    }


}
