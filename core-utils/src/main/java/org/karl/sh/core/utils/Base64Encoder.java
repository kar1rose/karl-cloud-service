package org.karl.sh.core.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * @author ROSE
 * @date 2019/7/30 14:38
 *
 **/
public class Base64Encoder {

    /**
     * @param bytes 待解密数组
     * @return 解密后的字节数组
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes 待加密字节数组
     * @return 加密完成的字节数组
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }
}
