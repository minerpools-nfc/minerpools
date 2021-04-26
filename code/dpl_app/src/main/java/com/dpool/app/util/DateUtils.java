package com.dpool.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils {
//    private static final DateTimeFormatter yyyyMMdd_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    /** yyyy-MM-dd HH */
//    private static final DateTimeFormatter yyyyMMddHH_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
//    /** yyyy-MM-dd HH:mm */
//    private static final DateTimeFormatter yyyyMMddHHmm_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//    /** yyyy-MM-dd HH:mm:ss */
//    private static final DateTimeFormatter yyyyMMddHHmmss_EN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    public static final long MIN = 60 * 1000L;
    public static final long HOUR = 60 * MIN;
    public static final long DAY = 24 * HOUR;
    public static final long WEEK = 7 * DAY;

    public static Date sub(Date date, long milliseconds) {
        return new Date(date.getTime() - milliseconds);
    }

    public static Date subMins(Date date, int howmany) {
        return sub(date, howmany * MIN);
    }

    public static Date subHour(Date date, int howmany) {
        return sub(date, howmany * HOUR);
    }

    public static Date subDay(Date date, int howmany) {
        return sub(date, howmany * DAY);
    }

    public static Date subWeek(Date date, int howmany) {
        return sub(date, howmany * WEEK);
    }

    public static Date add(Date date, long milliseconds) {
        return new Date(date.getTime() + milliseconds);
    }

    public static Date addMins(Date date, int howmany) {
        return add(date, howmany * MIN);
    }

    public static Date addHour(Date date, int howmany) {
        return add(date, howmany * HOUR);
    }

    public static Date addDay(Date date, int howmany) {
        return add(date, howmany * DAY);
    }

    public static Date addWeek(Date date, int howmany) {
        return add(date, howmany * WEEK);
    }

    public static Date getMonthFirstDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    public static String format(Date date) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
    public static String format(Date date,String fomart) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
    public static String formatDay(Date date) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    public static String formatDbDay(Date date) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }
    public static String formatMonth(Date date) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(date);
    }
    public static String Month(Date date) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM");
        return dateFormat.format(date);
    }
    public static Date paraseDate(String date) throws Exception{
    	 SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    	return dateFormat.parse(date);
    }
    public static Date paraseDate(String date,String format) throws Exception{
   	 SimpleDateFormat dateFormat=new SimpleDateFormat(format);
   	return dateFormat.parse(date);
   }
    public static String formatHour(Date date) {
    	SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
    	return sdf.format(date);
    }
}
