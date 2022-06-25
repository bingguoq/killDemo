package com.zzq.killdemo.service;

import com.zzq.killdemo.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzq.killdemo.vo.LoginVo;
import com.zzq.killdemo.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  用户服务接口
 * </p>
 *
 * @author 
 * @since 2022-05-01
 */
public interface UserService extends IService<User> {

    /**
     * 做登录
     *
     * @param loginVo  登录信息
     * @param request  请求
     * @param response 响应
     * @return {@link RespBean}
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);


    /**
     * 获取用户饼干
     *
     * @param userTicket sessionID
     * @param request    请求
     * @param response   响应
     * @return {@link User}
     */
    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);

}
