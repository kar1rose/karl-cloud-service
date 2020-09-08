package org.karl.sh.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author rose
 * @date 2018/6/12 14:11
 *
 */
public class Md5Util {


    /**
     * 加盐
     *
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuilder builder = new StringBuilder();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算 加盐
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    builder.append("0");
                }
                builder.append(str);
            }
            // 标准的md5加密后的结果
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 普通加密
     *
     * @param key
     * @return
     */
    public static String md5(String key) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }


}
