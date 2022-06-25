package com.zzq.killdemo.config;

import com.zzq.killdemo.pojo.User;
import com.zzq.killdemo.service.UserService;
import com.zzq.killdemo.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户参数解析器
 *
 * @author 224
 * @date 2022/06/25
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserService userService;

    /**
     * 判断支持参数的参数类型
     *
     * @param parameter 参数
     * @return boolean
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return parameterType== User.class;
    }

    /**
     * 参数解析
     *
     * @param parameter     参数
     * @param mavContainer  飞行器容器
     * @param webRequest    web请求
     * @param binderFactory 粘结剂工厂
     * @return {@link Object}
     * @throws Exception 异常
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse nativeResponse = webRequest.getNativeResponse(HttpServletResponse.class);
//        String userTicket = CookieUtil.getCookieValue(nativeRequest, "userTicket");
//        if (StringUtils.isEmpty(userTicket)) {
//            return null;
//        }
//        return userService.getUserByCookie(userTicket, nativeRequest, nativeResponse);
        return UserContext.getUser();
    }
}
