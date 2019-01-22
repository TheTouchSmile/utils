package com.smile.utils.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 年龄计算工具类
 *
 * @author xu
 * @date 2019/1/15 16:46
 */
public class AgeUtils {

    /**
     * 根据出生日期计算年龄
     * @param birthDayStr 出生日期
     * @return 年龄
     */
    public static int getAge(String birthDayStr) {
        //如果出生日期为null 返回0岁
        if (birthDayStr.isEmpty()) {
            return 0;
        }
        Date birthDay;
        try {
            birthDay = new SimpleDateFormat("yyyy-MM-dd").parse(birthDayStr);
            Calendar cal = Calendar.getInstance();

            //当前时间的年月日
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(birthDay);

            //出生日期的年月日
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

            int age = yearNow - yearBirth;

            //如果出生日期的月日,小于当前时间的月日年龄减一
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth)
                        age--;
                } else {
                    age--;
                }
            }
            return age;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;

    }
}
