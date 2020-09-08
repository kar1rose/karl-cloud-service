package org.karl.sh.core.utils;


import org.karl.sh.core.templates.BaseException;

/**
 * 自定义断言
 *
 * @author KARL ROSE
 * @date 2020/5/8 11:53
 **/
public class IAssert {

    /**
     * @param object  待判断的对象
     * @param message 异常信息
     **/
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BaseException(message);
        }
    }

    public static void notNullStr(String object, String message) {
        if (StringUtil.isEmpty(object)) {
            throw new BaseException(message);
        }
    }

    public static void equalsTo(int standard, int check, String message) {
        if (standard != check) {
            throw new BaseException(message);
        }
    }

}
