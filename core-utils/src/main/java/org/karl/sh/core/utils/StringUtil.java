package org.karl.sh.core.utils;


import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 操作类
 *
 * @author rose
 * @date 2019/3/14 16:18
 **/
public class StringUtil {

    private StringUtil() {
    }


    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取UUID最后12位并转大写
     *
     * @author rose
     * @date 2019/3/14 16:18
     **/
    public static String getUniqueId() {
        return UUID.randomUUID().toString().substring(24).toUpperCase();
    }

    /**
     * 小写UUID最后12位
     *
     * @author KARL.ROSE
     * @date 2019/8/26 11:38
     **/
    public static String getLowerUniqueId() {
        return UUID.randomUUID().toString().substring(24).toLowerCase();
    }

    /**
     * 判断字符串是否为空或者长度为0
     *
     * @author rose
     * @date 2019/6/27 13:44
     **/
    public static boolean isEmpty(final String value) {
        return value == null || value.trim().length() == 0 || "null".equals(value) || "undefind".equals(value);
    }

    public static boolean isNotEmpty(final String value) {
        return !isEmpty(value);
    }

    /**
     * 校验IP地址是否合法
     *
     * @author rose
     * @date 2019/7/15 16:52
     **/
    public static boolean ipValid(String ip) {
        String pattern = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(ip);
        return m.matches();
    }


    /**
     * 数字转换为中文大写
     *
     * @param money format example: 100.00
     * @return 中文大写字符串数组
     */
    public static String numToWord(String money) {
        int j = money.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < j; i++) {
            switch (money.charAt(i)) {
                case '0':
                    sb.append("零");
                    break;
                case '1':
                    sb.append("壹");
                    break;
                case '2':
                    sb.append("贰");
                    break;
                case '3':
                    sb.append("叁");
                    break;
                case '4':
                    sb.append("肆");
                    break;
                case '5':
                    sb.append("伍");
                    break;
                case '6':
                    sb.append("陆");
                    break;
                case '7':
                    sb.append("柒");
                    break;
                case '8':
                    sb.append("捌");
                    continue;
                case '9':
                    sb.append("玖");
                    break;
                case '.':
                    sb.append("点");
                    break;
                default:
            }
        }
        return new String(sb);
    }


    /**
     * 下划线命名转驼峰
     *
     * @author rose
     * @date 2019/6/27 13:48
     **/
    public static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        String value = str.toLowerCase();
        String[] fieldStr = value.split("_");
        for (String s : fieldStr) {
            sb.append(changeFirstCharacterCaseField(s, true));
        }
        return sb.toString();
    }

    public static String changeFirstCharacterCaseField(String str, boolean capitalize) {
        StringBuilder buf = new StringBuilder(str.length());
        if (capitalize) {
            buf.append(Character.toUpperCase(str.charAt(0)));
        } else {
            buf.append(Character.toLowerCase(str.charAt(0)));
        }
        buf.append(str.substring(1));
        return buf.toString();
    }


    /**
     * 截取中英文字符串
     */
    public static String subString(String text, int length, String endWith) {
        int textLength = text.length();
        int byteLength = 0;
        StringBuilder returnStr = new StringBuilder();
        for (int i = 0; i < textLength && byteLength < length * 2; i++) {
            String strI = text.substring(i, i + 1);
            if (strI.getBytes().length == 1) {
                // 英文
                byteLength++;
            } else {// 中文
                byteLength += 3;
            }
            returnStr.append(strI);
        }
        if (byteLength < text.getBytes(StandardCharsets.UTF_8).length) {
            // getBytes("GBK")每个汉字长2，getBytes("UTF-8")每个汉字长度为3
            returnStr.append(endWith);
        }
        return returnStr.toString();
    }

    /**
     * @author ROSE
     * unicode转换
     * @date 2019/1/14 12:00
     **/

    private static final Pattern STRING_PATTERN = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

    public static String decodeUnicode(String str) {
        Matcher matcher = STRING_PATTERN.matcher(str);
        char ch;
        while (matcher.find()) {
            String group = matcher.group(2);
            ch = (char) Integer.parseInt(group, 16);
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }


    /**
     * @author ROSE
     * string 数组转字符串
     * @date 2019/1/15 11:05
     **/
    public static String array2Str(String[] strings) {
        if (strings == null || strings.length == 0) {
            return "[]";
        } else {
            return Arrays.toString(strings);
        }
    }

    /**
     * 封装pageNum 与 pageSize
     *
     * @author rose
     * @date 2019/3/21 10:57
     **/
    public static String packLimitStr(Integer pageNum, Integer pageSize) {
        return " "
                .concat(String.valueOf((pageNum - 1) * pageSize))
                .concat(",").concat(String.valueOf(pageSize));
    }

    /**
     * 判断两个字符串是否相同（忽略大小写和字符顺序）
     *
     * @param s1
     * @param s2
     * @return
     */
    public static Boolean checkStrEquals(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        s1 = s1.toUpperCase();
        s2 = s2.toUpperCase();
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int temp = 0;
        for (char c1 : str1) {
            for (char c2 : str2) {
                if (c1 == c2) {
                    temp++;
                    continue;
                }
            }
        }
        if (str1.length != temp) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否不为空,如果不为空返回true 如果为空false
     *
     * @param str 传入要判断的字符串
     * @return [true | false]
     */
    public static boolean isNotNull(String str) {
        if (null == str || str.length() <= 0 || str.equals("null") || str.equals("") || "" == str) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个字符串数组是否相同，忽略顺序
     *
     * @param strings1
     * @param strings2
     * @return
     */
    public static Boolean checkStrArrayEquals(String[] strings1, String[] strings2) {
        if (strings1.length != strings2.length) {
            return false;
        }
        int temp = 0;
        for (String s1 : strings1) {
            for (String s2 : strings2) {
                if (s1.equals(s2)) {
                    temp++;
                    continue;
                }
            }
        }
        if (strings1.length != temp) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个字符串数组是否相同，判断顺序
     *
     * @param strings1
     * @param strings2
     * @return
     */
    public static Boolean checkStrArrayEqualsSort(String[] strings1, String[] strings2) {
        if (strings1.length != strings2.length) {
            return false;
        }
        for (int i = 0; i < strings1.length; i++) {
            if (!strings1[i].equals(strings2[i])) {
                return false;
            }
        }
        return true;
    }


    /**
     * arvato邮箱校验
     *
     * @author KARL.ROSE
     * @date 2019/9/3 10:42 上午
     **/
    public static boolean checkMail(String str) {
        String regexMail = "^[A-Za-z0-9.]+@arvato.+(com|cn)$";
        return str.matches(regexMail);
    }

    public static String formatStr(String str) {
        return str == null ? "" : str;
    }

    public static String isCheckNull(String str) {return str == "" ? null : str;}


    /**
     * 拼接微博地址
     *
     * @param province
     * @param city
     * @return
     */
    public static String spliceSinaAddress(String province, String city) {
        if (StringUtil.isEmpty(province) || StringUtil.isEmpty(city)) {
            return null;
        }
        province = "10" + province;
        if (city.length() == 1) {
            city = "00" + city;
        } else if (city.length() == 2) {
            city = "0" + city;
        }
        city = province + city;
        return city;
    }


    public static int StrToInteger(Object str) {
        int intStr = 0;
        try {
            intStr = Integer.parseInt(str.toString());
        } catch (NumberFormatException e) {

        }
        return intStr;
    }

    /**
     * 将list集合按照指定分隔符分割成字符串输出
     * [a,b,c,d] -> a,b,c,d
     *
     * @param list
     * @param separator 分隔符
     * @return
     */
    public static String listToStr(List<String> list, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        list.stream().forEach(str -> {
            stringBuilder.append(str);
            stringBuilder.append(separator);
        });
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
    }

    /**
     * 将字符串按照指定分隔符分割
     *
     * @param str
     * @param separator 分隔符
     * @return
     */
    public static List splitStr2List(String str, String separator) {
        List<String> result = new ArrayList<>();
        String[] strArr = str.split(separator);
        Arrays.stream(strArr).forEach(s -> result.add(s));
        return result;
    }

    /**
     * 手机号码格式校验
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        String regexPhone = "^1[3-9]\\d{9}$";
        return phone.matches(regexPhone);
    }

    /**
     * 生成6位验证码
     *
     * @param size 验证码的长度
     * @return
     */
    public static String generateCaptcha(int size) {
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        StringBuffer captcha = new StringBuffer();
        for (int i = 0; i < size; i++) {
            captcha.append(localRandom.nextInt(9));
        }
        return captcha.toString();
    }

    /**
     * 拼接sina省地址
     *
     * @param province
     * @return
     */
    public static String spliceSinaProvince(String province) {
        if (isEmpty(province)) {
            return null;
        }
        return "10" + province;
    }

    /**
     * 判断两个数组是否有交集
     *
     * @param keywords
     * @param results
     * @return
     */
    public static boolean haveIntersection(String[] keywords, String[] results) {
        if (keywords.length == 0 || results.length == 0) {
            return false;
        }
        for (String k : keywords) {
            for (String r : results) {
                if (r.equals(k)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 为指定字符串添加前缀和后缀
     *
     * @param str
     * @param prefix 前缀
     * @param suffix 后缀
     * @return
     */
    public static String appendPrefixAndSuffix(String str, String prefix, String suffix) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(suffix);
        stringBuilder.append(str);
        stringBuilder.append(suffix);
        return stringBuilder.toString();
    }
}
