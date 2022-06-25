package com.zzq.killdemo.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 限流注解
 *
 * @author 224
 * @date 2022/06/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    /**
     * 时间
     *
     * @return int
     */
    int second();

    /**
     * 最大访问数
     *
     * @return int
     */
    int maxCount();

    /**
     * 是否需要登录（默需要）
     *
     * @return boolean
     */
    boolean needLogin() default true;


}
