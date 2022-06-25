package com.zzq.killdemo.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.*;


/**
 * 用户
 *
 * @author 224
 * @date 2022/06/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID,手机号码
     */
    private Long id;

    /**
     * 昵称
     * */
    private String nickname;

    /**
     * MD5
     */
    private String password;


    /**
     * 盐值
     * */
    private String salt;

    /**
     * 头像
     */
    private String head;

    /**
     * 注册时间
     */
    private Date registerDate;

    /**
     * 最后一次登录时间
     */
    private Date lastLoginDate;

    /**
     * 登录次数
     */
    private Integer loginCount;


}
