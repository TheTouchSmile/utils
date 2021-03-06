package com.smile.utils.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author TheTouchSmile
 * @date 2019/1/22 10:29
 */
public class DateTimeUtils {

    /**
     * 获取当前日期(yyyy-MM-dd)
     *
     * @return 字符串类型当前日期
     */
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间(yyyy-MM-dd HH:mm:ss)
     *
     * @return 字符串类型当前时间
     */
    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 指定日期增加一年
     *
     * @param dateStr 指定日期
     * @return 加一年后的日期
     */
    public static String addYear(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 1);
            return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 指定日期增加一天
     *
     * @param dateStr 指定时间
     * @return 加1天后的时间
     */
    public static String addOneDay(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * unix转为标准时间类型
     *
     * @param unixStr  unix
     * @param dateType 所需时间日期格式
     * @return 转化后的标准日期
     */
    public static String unixTransformDate(long unixStr, String dateType) {
        return millisTransformDate(unixStr * 1000, dateType);
    }

    /**
     * 毫秒值时间转标准时间类型
     *
     * @param millis   毫秒值时间
     * @param dateType 所需时间日期格式
     * @return 转化后的标准日期
     */
    public static String millisTransformDate(long millis, String dateType) {
        Date date = new Date();
        date.setTime(millis);
        return new SimpleDateFormat(dateType).format(date);
    }

    /**
     * 两个时间(yyyy-MM-dd HH:mm:ss)的比较
     * 时间1大于时间2则返回true
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 结果
     */
    public static boolean dateCompare(String date1, String date2) {
        return dateTransform(date1) > dateTransform(date2);
    }

    /**
     * 将字符串格式时间转化为毫秒值
     *
     * @param date yyyy-MM-dd HH:mm:ss格式时间
     * @return 毫秒值
     */
    public static long dateTransform(String date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将字符串时间转化为毫秒值
        LocalDateTime time1 = LocalDateTime.parse(date, fmt);
        return time1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
