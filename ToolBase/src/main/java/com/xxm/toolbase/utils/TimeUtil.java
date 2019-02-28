package com.xxm.toolbase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/12/12 0012
 */
public class TimeUtil {

    public final static String patternAll = "yyyy-MM-dd HH:mm:ss";

    public final static String patternYMD = "yyyy-MM-dd";

    /**
     * 获取当前时间
     *
     * @param pattern
     * @return
     */
    public static String getCurDate(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date());
    }


    /**
     * 时间戳转换成字符窜
     *
     * @param milSecond
     * @param pattern
     * @return
     */
    public static String getDateToString(long milSecond, String pattern) {
        if (String.valueOf(milSecond).length() == 10)
            milSecond = milSecond * 1000;
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取随机账号系统时间
     * @return
     */
    public static String getRandomAccount(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);

        String monthStr;
        String dayStr;
        String hourStr;
        String minuteStr;
        String secondStr;
        String millisecondStr;

        if (month < 10)
            monthStr = "0" + month;
        else
            monthStr = String.valueOf(month);

        if (day < 10)
            dayStr = "0" + day;
        else
            dayStr = String.valueOf(day);

        if (hour < 10)
            hourStr = "0" + hour;
        else
            hourStr = String.valueOf(hour);

        if (minute < 10)
            minuteStr = "0" + minute;
        else
            minuteStr = String.valueOf(minute);

        if (second < 10)
            secondStr = "0" + second;
        else
            secondStr = String.valueOf(second);

        if (millisecond < 10){
            millisecondStr = "00" + millisecond;
        }else if(millisecond < 100){
            millisecondStr = "0" + millisecond;
        }else
            millisecondStr = String.valueOf(millisecond);


        StringBuilder sb = new StringBuilder();
        sb.append("1");
        sb.append(String.valueOf(calendar.get(Calendar.YEAR)).substring(2, 4));
        sb.append(monthStr);
        sb.append(dayStr);
        sb.append(hourStr);
        sb.append(minuteStr);
        sb.append(secondStr);
//        sb.append(millisecondStr);
        return sb.toString();
    }
}
