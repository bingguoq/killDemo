package com.zzq.killdemo.vo;

/**
 * 返回状态枚举类
 *
 * @author 224
 * @date 2022/06/25
 */
public enum RespBeanEnum {

    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    Login_ERROR(500210,"用户名或密码错误"),
    MOBILE_ERROR(500211,"手机号码格式错误"),
    BIND_ERROR(200212,"参数校验异常"),
    EMPTY_STOCK(500500,"库存不足"),
    REPEATE_ERROR(500501,"该商品每人限购一件"),
    SESSION_ERROR(500215,"登录异常"),
    ORDER_NOT_EXIST(500300,"订单信息不存在"),
    REQUEST_ILLEGAL(500502,"请求非法"),
    ERROR_CAPTCHA(500503,"验证码错误"),
    ACCESS_LIMIT_REAHCED(500504,"请求频繁，请稍后再试");


    /** 响应状态码 */
    private final Integer code;
    /** 响应信息 */
    private final String message;

    RespBeanEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "RespBeanEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
