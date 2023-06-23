/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/6/18 22:14
 */


package com.hwj.system.custom;

import com.hwj.common.untils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/*
    * @title: 自定义MD5加密
    * @description: 自定义MD5加密
 */
@Component
public class CustomMd5PasswordEncoder implements PasswordEncoder {
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}
