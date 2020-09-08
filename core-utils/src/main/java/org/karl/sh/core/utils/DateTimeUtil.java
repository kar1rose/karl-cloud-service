package org.karl.sh.core.utils;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * jdk8 时间操作类
 *
 * @author karl.rose
 * @date 2020/4/23 10:51
 **/
public class DateTimeUtil {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static String DATE_PATTERN = "yyyy-MM-dd";
    private final static String DATE_PATH = "yyyy/MM/dd/";
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
    private final static DateTimeFormatter DATE_PATH_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATH);
    private final static ZoneOffset DEFAULT_ZONE_OFFSET = ZoneOffset.of("+8");
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
    private final static SimpleDateFormat SIMPLE_DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_PATTERN);


    public static String dateTimeStr(LocalDateTime localDateTime) {
        return DATE_TIME_FORMATTER.format(localDateTime);
    }

    /**
     * localDateTime 转 java.util.Date
     *
     * @param localDateTime 时间入参
     * @author KARL.ROSE
     * @date 2020/4/23 11:22
     **/
    public static Date dateTime(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDateTime.atZone(DEFAULT_ZONE_OFFSET);
        return Date.from(zonedDateTime.toInstant());
    }

    public static String dateStr(LocalDate date) {
        return DATE_FORMATTER.format(date);

    }

    /**
     * localDate 转 java.util.Date
     *
     * @param localDate 时间参数
     * @author KARL.ROSE
     * @date 2020/4/23 11:19
     **/
    public static Date date(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(DEFAULT_ZONE_OFFSET);
        return Date.from(zonedDateTime.toInstant());

    }

    /**
     * 字符串转date
     *
     * @param dateTimeStr 日期时间字符串
     * @return java.util.Date
     * @author KARL.ROSE
     * @date 2020/5/31 15:27
     **/
    public static Date convertDateTimeStrToDate(String dateTimeStr) {
        return dateTime(LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER));
    }

    /**
     * 计算两个日期之间的天数之差 (绝对值）
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long daysInterval(Date startTime, Date endTime) {
        long result = millisInterval(startTime, endTime) / (60 * 60 * 24 * 1000);
        return result > 0 ? result : -result;
    }

    /**
     * 计算两个日期之间的分钟数之差（绝对值）
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long minsInterval(Date startTime, Date endTime) {
        long result = millisInterval(startTime, endTime) / (60 * 1000);
        return result > 0 ? result : -result;
    }

    /**
     * 计算两个日期之间的毫秒数之差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long millisInterval(Date startTime, Date endTime) {
        return startTime.getTime() - endTime.getTime();
    }

    public static Date convertDateStrToDate(String dateStr) {
        return date(LocalDate.parse(dateStr, DATE_FORMATTER));
    }

    public static String now() {
        return dateTimeStr(LocalDateTime.now());
    }

    public static String today() {
        return dateStr(LocalDate.now());
    }

    public static String getDatePath(LocalDate localDate) {
        return DATE_PATH_FORMATTER.format(localDate);
    }

    public static String getDatePath() {
        return DATE_PATH_FORMATTER.format(LocalDate.now());
    }


    /**
     * 2个时间比较
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     **/
    public static int DateCompare(Date beginTime, Date endTime) {
        int compareTo = beginTime.compareTo(endTime);
        return compareTo;
    }

//    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date ss = sdf.parse("2020-08-01 10:12:13");
//        System.out.println(DateCompare(new Date(),ss));
//
//    }

    /**
     * 获取今天开始时间
     */
    public static String getStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(new Date()) + " 00:00:00";
    }

    /**
     * 获取今天结束时间
     */
    public static String getEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(new Date()) + " 23:59:59";
    }

    public static boolean isThisTime(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        String param = sdf.format(time);//参数时间
        String now = sdf.format(new Date());//当前时间
        if(param.equals(now)){
            return true;
        }
        return false;
    }

}
