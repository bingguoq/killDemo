package com.zzq.killdemo.controller;

import com.zzq.killdemo.service.UserService;
import com.zzq.killdemo.vo.LoginVo;
import com.zzq.killdemo.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * 登录控制器
 *
 * @author 224
 * @date 2022/06/24
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {


    @Autowired
    UserService userService;

    /**
     * 登录页面
     * @return {@link String}
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 做登录
     *
     * @param loginVo  登录信息
     * @param request  请求
     * @param response 响应
     * @return {@link RespBean}
     */
    @ResponseBody
    @RequestMapping("/doLogin")
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        return userService.doLogin(loginVo,request,response);
    }
}
