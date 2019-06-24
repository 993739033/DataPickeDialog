package com.scode.datapickedialog.DateDialog;

/**
 * Created by 张皓然 on 2018/3/5.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Copy by zwx
 * "HH:mm:ss"是24小时制的，"hh:mm:ss"是12小时制。
 */
@SuppressWarnings("deprecation")
public class TimeUtil {

    public static String dateFormat_day = "HH:mm";
    public static String dateFormat_month = "MM-dd";

    /**
     * 时间转换成字符串,默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @param time 时间
     */
    public static String dateToString(long time) {
        return dateToString(time, "yyyy.MM.dd HH:mm");
    }

    /**
     * 时间转换成字符串,指定格式
     *
     * @param time   时间
     * @param format 时间格式
     */
    public static String dateToString(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }


    /**
     * 从时间(毫秒)中提取出日期
     *
     * @param millisecond
     * @return
     */
    public static String getDateFromMillisecond(Long millisecond) {

        Date date = null;
        try {
            date = new Date(millisecond);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar current = Calendar.getInstance();
        ////今天
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        //昨天
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        // 今年
        Calendar thisYear = Calendar.getInstance();
        thisYear.set(Calendar.YEAR, current.get(Calendar.YEAR));
        thisYear.set(Calendar.MONTH, 0);
        thisYear.set(Calendar.DAY_OF_MONTH, 0);
        thisYear.set(Calendar.HOUR_OF_DAY, 0);
        thisYear.set(Calendar.MINUTE, 0);
        thisYear.set(Calendar.SECOND, 0);
        current.setTime(date);
        //今年以前
        if (current.before(thisYear)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = format.format(date);
            return dateStr;
        } else if (current.after(today)) {
            return "今天";
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天";
        } else {
            SimpleDateFormat format = new SimpleDateFormat("MM-dd");
            String dateStr = format.format(date);
            return dateStr;
        }
    }

    /**
     * 从时间(毫秒)中提取出时间(时:分)
     * 时间格式:  时:分
     *
     * @param millisecond
     * @return
     */
    public static String getTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(millisecond);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }

    /**
     * 获取转换后的时间信息
     *
     * @param millisecond
     * @return
     */

    public static Date getDateFromMilliseconds(String millisecond) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(millisecond);
        return date;
    }


    public static Date getDateFromTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(time);
        return date;
    }


    //获取小时分钟的String值
    public static String getHHmmFromTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        String minute = "00";
        if (date.getMinutes() < 10) {
            minute = "0" + date.getMinutes();
        } else {
            minute = date.getMinutes() + "";
        }
        String HHMM = date.getHours() + ":" + minute;
        return HHMM;
    }

    //获取日小时分钟的String值
    public static String getddHHmmFromTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        String minute = "00";
        if (date.getMinutes() < 10) {
            minute = "0" + date.getMinutes();
        } else {
            minute = date.getMinutes() + "";
        }
        String HHMM = date.getDay() + "日 " + date.getHours() + ":" + minute;
        return HHMM;
    }


    //获取日小时分钟的String[]值
    public static String[] getArraysFromTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String[] strings = new String[5];
        strings[0] = calendar.get(Calendar.YEAR) + "";
        strings[1] = (calendar.get(Calendar.MONTH) + 1) + "";
        strings[2] = (calendar.get(Calendar.DAY_OF_MONTH)) + "";
        strings[3] = AutoAddZero((calendar.get(Calendar.HOUR_OF_DAY))) + "";
        strings[4] = AutoAddZero((calendar.get(Calendar.MINUTE))) + "";
        return strings;
    }

    //获取年月日
    public static String[] getArraysFromTime1(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(time);
        String[] strings = new String[3];
        strings[0] = date.getYear() + "";
        strings[1] = (date.getMonth() + 1) + "";
        strings[2] = (date.getDay()) + "";
        return strings;
    }

    //获取年月
    public static String[] getArraysFromTime2(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = simpleDateFormat.parse(time);
        String[] strings = new String[2];
        strings[0] = date.getYear() + "";
        strings[1] = (date.getMonth() + 1) + "";
        return strings;
    }

    //自动补全小时分钟小于10前面添加0
    public static String AutoAddZero(int i) {
        String i1 = i + "";
        if (i >= 0 && i < 10) {
            i1 = "0" + i;
        }
        return i1;
    }


    //获取年月日的String值
    public static String getyyyyMMddFromTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String yyyyMMdd = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        return yyyyMMdd;
    }


    //获取年月日转换为秒数
    public static long getSecondFromTime(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(time);
        return (long) (date.getTime() / 1000);
    }


    /**
     * 将毫秒转化成固定格式的时间
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param millisecond
     * @return
     */
    public static String getDateTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 获取当前时间
     * <p>
     * 时间格式: yyyy-MM-dd HH:mm:ss
     */

    public static String getNowTime() {
        long current = System.currentTimeMillis();
        return getDateTimeFromMillisecond(current);
    }


    //获取当前时间的年月日
    public static String getNowTimeyyMMdd() {
        String date = null;
        try {
            date = getyyyyMMddFromTime(getNowTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //获取当前时间的时分
    public static String getNowTimehhmm() {
        String time = null;
        try {
            time = getHHmmFromTime(getNowTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }


    /**
     * 获取当前时间的long值
     */
    public static long getNowTimeLong() {
        return System.currentTimeMillis();
    }


    /**
     * 将时间转化成毫秒
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static Long timeStrToSecond(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long second = format.parse(time).getTime();
            return second;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }

    /**
     * 获取时间间隔
     *
     * @param millisecond
     * @return
     */
    public static String getSpaceTime(Long millisecond) {
        Calendar calendar = Calendar.getInstance();
        Long currentMillisecond = calendar.getTimeInMillis();

        //间隔秒
        Long spaceSecond = (currentMillisecond - millisecond) / 1000;

        //一分钟之内
        if (spaceSecond >= 0 && spaceSecond < 60) {
            return "片刻之前";
        }
        //一小时之内
        else if (spaceSecond / 60 > 0 && spaceSecond / 60 < 60) {
            return spaceSecond / 60 + "分钟之前";
        }
        //一天之内
        else if (spaceSecond / (60 * 60) > 0 && spaceSecond / (60 * 60) < 24) {
            return spaceSecond / (60 * 60) + "小时之前";
        }
        //3天之内
        else if (spaceSecond / (60 * 60 * 24) > 0 && spaceSecond / (60 * 60 * 24) < 3) {
            return spaceSecond / (60 * 60 * 24) + "天之前";
        } else {
            return getDateTimeFromMillisecond(millisecond);
        }
    }

    /**
     * 获取年
     * @return
     */
    public static int getYear(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.YEAR);
    }
    /**
     * 获取月
     * @return
     */
    public static int getMonth(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MONTH)+1;
    }
    /**
     * 获取日
     * @return
     */
    public static int getDay(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.DATE);
    }
    /**
     * 获取时
     * @return
     */
    public static int getHour(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.HOUR);
    }
    /**
     * 获取分
     * @return
     */
    public static int getMinute(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MINUTE);
    }


}
