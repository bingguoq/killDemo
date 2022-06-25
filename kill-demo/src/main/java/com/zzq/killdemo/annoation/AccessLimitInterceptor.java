package com.zzq.killdemo.annoation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzq.killdemo.annoation.AccessLimit;
import com.zzq.killdemo.config.UserContext;
import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.service.UserService;
import com.zzq.killdemo.utils.CookieUtil;
import com.zzq.killdemo.vo.RespBean;
import com.zzq.killdemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


/**
 * 限流拦截器
 *
 * @author 224
 * @date 2022/06/25
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Autowired
    StringRedisTemplate redis;

    /**
     * 前置处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @return boolean
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            //获取当前登录的用户信息
            User user = getUser(request, response);
            //设置到ThreadLocal中
            UserContext.setUser(user);
            //获取线程名
            String name = Thread.currentThread().getName();
            HandlerMethod hm= (HandlerMethod)handler;
            //获取注解信息
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit==null){//若没有设置值，直接放行
                return true;
            }
            //获取限流时间
            int second = accessLimit.second();
            //获取最大访问数
            int maxCount = accessLimit.maxCount();
            //获取是否需要用户登录
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if(needLogin){//如果需要登录
                if(user==null){//如果用户为空
                    //返回需要用户登录的信息，之后拦截
                    sender(response, RespBeanEnum.SESSION_ERROR);
                    return false;
                }
                key+=":"+user.getId();
            }
            //开始计数限流
            String s = redis.opsForValue().get(key);
            Integer count =0;
            if(s==null){
                redis.opsForValue().set(key, String.valueOf(1),second, TimeUnit.SECONDS);
            }else{
                 count = Integer.valueOf(s);
            }
            //未超过最大访问数
            if(count<maxCount){
                redis.opsForValue().increment(key);
            }else{//超过最大访问数
                sender(response,RespBeanEnum.ACCESS_LIMIT_REAHCED);
                return false;
            }
        }
        return true;
    }

    /**
     * 返回需要用户登录的信息
     *
     * @param response     响应
     * @param respBeanEnum resp bean枚举
     * @throws IOException ioexception
     */
    private void sender(HttpServletResponse response, RespBeanEnum respBeanEnum) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        RespBean bean = RespBean.error(respBeanEnum);
        printWriter.write(new ObjectMapper().writeValueAsString(bean));
        printWriter.flush();
        printWriter.close();
    }


    /**
     * 获取用户信息
     *
     * @param request  请求
     * @param response 响应
     * @return {@link User}
     */
    private User getUser(HttpServletRequest request, HttpServletResponse response){
        //从cookie中获取sessionId
        String userTicket = CookieUtil.getCookieValue(request, "userTicket");
        //为sessionId为空，则返回null
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        //返回用户信息
        return userService.getUserByCookie(userTicket, request, response);
    }

}
