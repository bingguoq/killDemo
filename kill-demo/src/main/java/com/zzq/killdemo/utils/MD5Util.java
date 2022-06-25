package com.zzq.killdemo.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;

/**
 * md5工具类
 *
 * @author 224
 * @date 2022/06/25
 */
@Component
public class MD5Util {

    public static String md5(String s){
        return DigestUtils.md5DigestAsHex(s.getBytes(StandardCharsets.UTF_8));
    }

    private static final String salt="1a2b3c4d";

    /**
     * 第一次加密（明文密码->第一次加密）
     *
     * @param inputPass 输入通过
     * @return {@link String}
     */
    public static String inputPassToFromPass(String inputPass){
        String s= "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(s);
    }


    /**
     * 二次加密（第一次加密->第二次加密）
     *
     * @param frommPass 弗洛姆通过
     * @param salt      盐
     * @return {@link String}
     */
    public static String fromPassToDBPass(String frommPass,String salt){
        String s= "" + salt.charAt(0) + salt.charAt(2) + frommPass + salt.charAt(5) + salt.charAt(4);
        return md5(s);
    }

    /**
     * 明文密码->最终加密
     *
     * @param salt      盐值
     * @param inputPass 明文密码
     * @return {@link String}
     */
    public static String inputPassToDBPass(String inputPass,String salt){
       String fromPass =inputPassToFromPass(inputPass);
       String dbPass=fromPassToDBPass(fromPass,salt);
       return dbPass;
    }

//    public static void main(String[] args) {
//        System.out.println(inputPassToFromPass("123456"));
//        System.out.println(fromPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9",salt));
//        System.out.println(inputPassToDBPass("123456",salt));
//        //d3b1294a61a07da9b49b6e22b2cbd7f9
//    }

}
