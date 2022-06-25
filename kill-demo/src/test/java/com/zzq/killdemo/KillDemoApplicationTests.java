package com.zzq.killdemo;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzq.killdemo.mapper.GoodMessageMapper;
import com.zzq.killdemo.mapper.KillOrderMapper;
import com.zzq.killdemo.mapper.UserMapper;
import com.zzq.killdemo.pojo.GoodMessage;
import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.rabbitmq.MQSender;
import com.zzq.killdemo.service.GoodMessageService;
import com.zzq.killdemo.utils.MD5Util;
import com.zzq.killdemo.utils.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootTest
class KillDemoApplicationTests {

    @Autowired
    KillOrderMapper killOrderMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate redis;

    //秒杀接口压测工具
    @Test
    public void test() throws IOException {
        File file = new File("C:\\Users\\20213\\Desktop\\b.txt");
        FileOutputStream out = new FileOutputStream(file);
        List<User> users = userMapper.selectList(new QueryWrapper<User>());
        for (User user : users) {
            String ticket = UUIDUtil.uuid();
            redis.opsForValue().set("user:" + ticket, JSON.toJSONString(user));
            String res = user.getId() + "," + ticket + "\n";
            out.write(res.getBytes(StandardCharsets.UTF_8));
        }
    }

    //用户生成
    @Test
    public void test2() throws IOException {
        for (int i = 0; i < 79; i++) {
            User user = new User();
            user.setId(13000100020L + i);
            user.setNickname("usersss" + i);
            user.setSalt("1a2b3c4d");
            user.setPassword(MD5Util.inputPassToDBPass("123465", user.getSalt()));
            userMapper.insert(user);
        }
    }

    @Autowired
    GoodMessageService goodMessageService;

    @Autowired
    MQSender mqSender;

    @Test
    public void testt123(){
//        List<GoodMessage> messages = goodMessageService.selectNoConfirmMessageList();
//        for (GoodMessage message : messages) {
//            System.out.println(message);
//            mqSender.sendKillMessage(JSON.toJSONString(message));
//        }

        goodMessageService.addMessage(new GoodMessage("123",1L,1L,1));
    }



}
