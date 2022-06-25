package com.zzq.killdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.zzq.killdemo.exception.GlobalException;
import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.mapper.UserMapper;
import com.zzq.killdemo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzq.killdemo.utils.CookieUtil;
import com.zzq.killdemo.utils.MD5Util;
import com.zzq.killdemo.utils.UUIDUtil;
import com.zzq.killdemo.vo.LoginVo;
import com.zzq.killdemo.vo.RespBean;
import com.zzq.killdemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  用户登录服务实现类
 * </p>
 *
 * @author 
 * @since 2022-05-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    StringRedisTemplate redis;

    /**
     * 登录实现
     *
     * @param loginVo  用户登录信息
     * @param request  请求
     * @param response 响应
     * @return {@link RespBean}
     */
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request,HttpServletResponse response) {

        //获取用户输入的手机号和密码（这里的密码已经被前端进行了第一次加密）
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //根据手机号从数据库中查询用户信息
        User user=userMapper.selectById(Long.valueOf(mobile));

        //如果查询出的用户为空或者密码不正确，则抛出登录异常
        if(user==null||!MD5Util.fromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(RespBeanEnum.Login_ERROR);
        }

        //生成并设置cookie，并将用户信息其存入redis中
        String ticket = UUIDUtil.uuid();
        redis.opsForValue().set("user:"+ticket, JSON.toJSONString(user));
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        return RespBean.success();
    }

    /**
     * 通过cookie获取用户信息
     *
     * @param userTicket 即sessionID
     * @param request    请求
     * @param response   响应
     * @return {@link User}
     */
    @Override
    public User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response) {
        //如果sessionID为空，则表示用户没有登录或者登录已经过期，返回空
        if(StringUtils.isEmpty(userTicket)){
            return null;
        }
        //若不为空，则通过sessionID从redis中获取用户信息
        String s = redis.opsForValue().get("user:" + userTicket);
        User user = JSON.toJavaObject(JSON.parseObject(s), User.class);

        //若用户不为空则重新设置cookie的过期时间
        if(user!=null){
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }

}
