package org.karl.sh.core.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author KARL ROSE
 * @date 2020/9/8 14:22
 **/
public class PwdUtils {

    public static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String encrypt(String password) {
        return ENCODER.encode(password);
    }

    public static void main(String[] args) {
        System.out.println(encrypt("karl"));
    }
}
