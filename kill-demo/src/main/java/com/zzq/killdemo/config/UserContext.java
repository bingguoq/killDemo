package com.zzq.killdemo.config;

import com.zzq.killdemo.pojo.User;


/**
 * 用户上下文
 *
 * @author 224
 * @date 2022/06/25
 */
public class UserContext {

    private static ThreadLocal<User> userHolder=new ThreadLocal<>();

    public static void setUser(User user){
        userHolder.set(user);
    }

    public static User getUser(){
        return userHolder.get();
    }
}
