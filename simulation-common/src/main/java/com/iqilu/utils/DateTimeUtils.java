package com.iqilu.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 日期处理工具
 *
 * @author zhangyicheng
 * @date 2020/05/22
 */
public class DateTimeUtils {

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前秒级时间戳
     *
     * @return 当前时间戳
     */
    public static int nowSec() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 获取传入日期的秒级时间戳
     *
     * @param dateTime 日期
     * @author zhangyicheng
     * @date 2020/07/04
     */
    public static int getSecondTimestampTwo(Date dateTime) {
        if (null == dateTime) {
            return 0;
        }
        String timestamp = String.valueOf(dateTime.getTime() / 1000);
        return Integer.parseInt(timestamp);
    }

    /**
     * 获取当前时间字符串
     */
    public static String currentTimeStr() {
        return currentTimeStr("");
    }

    /**
     * 获取当前时间字符串
     *
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     */
    public static String currentTimeStr(String pattern) {
        if (CommonUtils.isEmpty(pattern)) {
            pattern = DEFAULT_TIME_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
    }

    /**
     * 获取当前日期字符串
     */
    public static String currentDateStr() {
        return currentDateStr(DEFAULT_DATE_PATTERN);
    }

    /**
     * 获取当前日期字符串
     *
     * @param pattern 格式，默认：yyyy-MM-dd
     */
    public static String currentDateStr(String pattern) {
        if (CommonUtils.isEmpty(pattern)) {
            pattern = DEFAULT_DATE_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
    }

    /**
     * 获取当前周的日期集合
     */
    public static String[] currentWeekDaysStr() {
        return currentWeekDaysStr(DEFAULT_DATE_PATTERN);
    }

    /**
     * 获取当前周的日期集合
     *
     * @param pattern 格式，默认：yyyy-MM-dd
     */
    public static String[] currentWeekDaysStr(String pattern) {
        if (CommonUtils.isEmpty(pattern)) {
            pattern = DEFAULT_DATE_PATTERN;
        }
        String[] weekDates = {"", "", "", "", "", "", ""};
        int dayOfWeek = dayOfWeek();
        switch (dayOfWeek) {
            case 1:
                weekDates[0] = currentDateStr();
                weekDates[1] = plusDays(1, pattern);
                weekDates[2] = plusDays(2, pattern);
                weekDates[3] = plusDays(3, pattern);
                weekDates[4] = plusDays(4, pattern);
                weekDates[5] = plusDays(5, pattern);
                weekDates[6] = plusDays(6, pattern);
                break;
            case 2:
                weekDates[0] = minusDays(1, pattern);
                weekDates[1] = currentDateStr();
                weekDates[2] = plusDays(1, pattern);
                weekDates[3] = plusDays(2, pattern);
                weekDates[4] = plusDays(3, pattern);
                weekDates[5] = plusDays(4, pattern);
                weekDates[6] = plusDays(5, pattern);
                break;
            case 3:
                weekDates[0] = minusDays(2, pattern);
                weekDates[1] = minusDays(1, pattern);
                weekDates[2] = currentDateStr();
                weekDates[3] = plusDays(1, pattern);
                weekDates[4] = plusDays(2, pattern);
                weekDates[5] = plusDays(3, pattern);
                weekDates[6] = plusDays(4, pattern);
                break;
            case 4:
                weekDates[0] = minusDays(3, pattern);
                weekDates[1] = minusDays(2, pattern);
                weekDates[2] = minusDays(1, pattern);
                weekDates[3] = currentDateStr();
                weekDates[4] = plusDays(1, pattern);
                weekDates[5] = plusDays(2, pattern);
                weekDates[6] = plusDays(3, pattern);
                break;
            case 5:
                weekDates[0] = minusDays(4, pattern);
                weekDates[1] = minusDays(3, pattern);
                weekDates[2] = minusDays(2, pattern);
                weekDates[3] = minusDays(1, pattern);
                weekDates[4] = currentDateStr();
                weekDates[5] = plusDays(1, pattern);
                weekDates[6] = plusDays(2, pattern);
                break;
            case 6:
                weekDates[0] = minusDays(5, pattern);
                weekDates[1] = minusDays(4, pattern);
                weekDates[2] = minusDays(3, pattern);
                weekDates[3] = minusDays(2, pattern);
                weekDates[4] = minusDays(1, pattern);
                weekDates[5] = currentDateStr();
                weekDates[6] = plusDays(1, pattern);
                break;
            case 7:
                weekDates[0] = minusDays(6, pattern);
                weekDates[1] = minusDays(5, pattern);
                weekDates[2] = minusDays(4, pattern);
                weekDates[3] = minusDays(3, pattern);
                weekDates[4] = minusDays(2, pattern);
                weekDates[5] = minusDays(1, pattern);
                weekDates[6] = currentDateStr();
                break;
            default:
        }
        return weekDates;
    }

    /**
     * 增加分钟数
     *
     * @param minutes 分钟数
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     * @return 日期字符串
     */
    public static String plusMinutes(int minutes, String pattern) {
        if (CommonUtils.isEmpty(pattern)) {
            pattern = DEFAULT_TIME_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now(ZoneOffset.of("+8")).plusMinutes(minutes).format(formatter);
    }

    /**
     * 减少分钟数
     *
     * @param minutes 分钟数
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     * @return 日期字符串
     */
    public static String minusMinutes(int minutes, String pattern) {
        if (CommonUtils.isEmpty(pattern)) {
            pattern = DEFAULT_TIME_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now(ZoneOffset.of("+8")).minusMinutes(minutes).format(formatter);
    }

    /**
     * 减少分钟，并转为时间戳
     *
     * @param minutes 分钟数
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     */
    public static long plusMinutesToTimestamp(int minutes, String pattern) {
        return timeToTimestamp(plusMinutes(minutes, pattern), pattern);
    }

    /**
     * 减少分钟，并转为时间戳
     *
     * @param minutes 分钟数
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     */
    public static long minusMinutesToTimestamp(int minutes, String pattern) {
        return timeToTimestamp(minusMinutes(minutes, pattern), pattern);
    }

    /**
     * 增加天数
     *
     * @param days    天数
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     */
    public static String plusDays(int days, String pattern) {
        if (CommonUtils.isEmpty(pattern)) {
            pattern = DEFAULT_TIME_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now(ZoneOffset.of("+8")).plusDays(days).format(formatter);
    }

    /**
     * 增加天数并转化为时间戳
     *
     * @param days    天数
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     */
    public static long plusDaysToTimestamp(int days, String pattern) {
        return timeToTimestamp(plusDays(days, pattern), pattern);
    }

    /**
     * 减少天数
     *
     * @param days    天数
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     */
    public static String minusDays(int days, String pattern) {
        if (CommonUtils.isEmpty(pattern)) {
            pattern = DEFAULT_TIME_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now(ZoneOffset.of("+8")).minusDays(days).format(formatter);
    }

    /**
     * 减少天数并转化为时间戳
     *
     * @param days    天数
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     */
    public static long minusDaysToTimestamp(int days, String pattern) {
        return timeToTimestamp(minusDays(days, pattern), pattern);
    }

    /**
     * 时间戳转时间字符串
     *
     * @param timestamp long类型时间戳 毫秒级
     */
    public static String timestampToTime(long timestamp) {
        return timestampToTime(timestamp, "");
    }

    /**
     * 时间戳转时间字符串
     *
     * @param ts      long类型时间戳 毫秒级
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     */
    public static String timestampToTime(long ts, String pattern) {
        if (CommonUtils.isEmpty(pattern)) {
            pattern = DEFAULT_TIME_PATTERN;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(ts);
    }

    /**
     * 时间字符串转时间戳
     *
     * @param time 时间字符串
     * @return 毫秒级时间戳
     */
    public static long timeToTimestamp(String time) {
        return timeToTimestamp(time, "");
    }

    /**
     * 时间字符串转时间戳
     *
     * @param time    时间字符串
     * @param pattern 格式，默认：yyyy-MM-dd HH:mm:ss
     * @return 毫秒级时间戳
     */
    public static long timeToTimestamp(String time, String pattern) {
        try {
            if (CommonUtils.isEmpty(pattern)) {
                pattern = DEFAULT_TIME_PATTERN;
            }
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式，默认：yyyy-MM-dd
     */
    public static String formatDate(String date, String pattern) {
        try {
            if (CommonUtils.isEmpty(pattern)) {
                pattern = DEFAULT_DATE_PATTERN;
            }
            DateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    /**
     * 今天是周几(第一天为周日)
     */
    public static int dayOfWeek() {
        return dayOfWeek(true);
    }

    /**
     * 今天是周几
     *
     * @param firstDayIsSunday 周日为开始日
     */
    public static int dayOfWeek(boolean firstDayIsSunday) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
            Date today = format.parse(currentDateStr());
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            int weekDay = c.get(Calendar.DAY_OF_WEEK);
            if (firstDayIsSunday) {
                return weekDay;
            }
            return weekDay + 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取某月的最后一天
     *
     * @param firstDayOfMonth 第一天
     * @param pattern         格式，默认：yyyy-MM-dd
     */
    public static String getLastDayOfMonth(String firstDayOfMonth, String pattern) {
        try {
            if (CommonUtils.isEmpty(pattern)) {
                pattern = DEFAULT_DATE_PATTERN;
            }

            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date date = format.parse(firstDayOfMonth);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            // 获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            // 设置日历中月份的最大天数
            cal.set(Calendar.DAY_OF_MONTH, lastDay);
            //格式化日期
            return format.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取某月有多少天
     *
     * @param firstDayOfMonth 第一天
     * @param pattern         格式，默认：yyyy-MM-dd
     */
    public static int getDaysOfMonth(String firstDayOfMonth, String pattern) {
        try {
            if (CommonUtils.isEmpty(pattern)) {
                pattern = DEFAULT_DATE_PATTERN;
            }

            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date date = formatter.parse(firstDayOfMonth);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            // 获取某月最小天数和最大天数
            int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            return lastDay - firstDay + 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取七天前的日期 (包含当天)
     *
     * @author zhangyicheng
     * @date 2020/09/14
     */
    public static String[] getBeforeSevenDay() {
        String[] arr = new String[7];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = null;
        int week = 7;
        for (int i = 0; i < week; i++) {
            c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -i);
            arr[6 - i] = sdf.format(c.getTime());
        }
        return arr;
    }

    /**
     * 获取前12个月月份 (包含当月)
     *
     * @author zhangyicheng
     * @date 2020/09/14
     */
    public static String[] getBeforeTwelveMonth() {
        String[] last12Months = new String[12];
        Calendar cal = Calendar.getInstance();
        // 如果当前日期大于二月份的天数28天或者29天会导致计算月份错误，会多出一个三月份，故设置一个靠前日期解决此问题
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int month = 12;
        for (int i = 0; i < month; i++) {
            if (i < 9) {
                last12Months[11 - i] = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1);
            } else {
                last12Months[11 - i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1);
            }
            // 逐次往前推1个月
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        }
        return last12Months;
    }

    /**
     * 获取今天日期及所在一周的周一和周日日期.
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static Map<String, String> getWeekDate() {
        Map<String, String> dateMap = new HashMap<>(16);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            dayWeek = 8;
        }
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);
        Date mondayDate = cal.getTime();
        String weekBegin = sdf.format(mondayDate);

        cal.add(Calendar.DATE, 4 + cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        String weekEnd = sdf.format(sundayDate);

        dateMap.put("todayDate", sdf.format(cal.getTime()));
        dateMap.put("mondayDate", weekBegin);
        dateMap.put("sundayDate", weekEnd);

        return dateMap;
    }

    /**
     * 获取计算日期的前后具体天数日期
     *
     * @param date     计算的日期 格式: yyyy-MM-dd
     * @param quantity 计算日期的前后天数
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String lastOrLaterDate(String date, int quantity) {

        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        String nextDay = "";
        try {
            Date temp = dft.parse(date);
            Calendar cld = Calendar.getInstance();
            cld.setTime(temp);
            cld.add(Calendar.DATE, quantity);
            temp = cld.getTime();
            nextDay = dft.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextDay;
    }

    /**
     * 获取过去或未来第几天的日期
     *
     * @param past         第几天日期
     * @param date         日期
     * @param frontAndBack 过去/未来 true/false
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String getPastOrFutureDate(int past, Date date, boolean frontAndBack) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (frontAndBack) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - past);
        } else {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + past);
        }
        Date today = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(today);
    }

    /**
     * 获取某之前/后 N天的日期
     *
     * @param date         日期 yyyy-MM-dd
     * @param quantity     前天数
     * @param frontAndBack 前/后 true/false
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static ArrayList<String> pastOrFutureDay(String date, int quantity, boolean frontAndBack) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(date);
            for (int i = quantity - 1; i >= 0; i--) {
                pastDaysList.add(getPastOrFutureDate(i, d, frontAndBack));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!frontAndBack) {
            Collections.reverse(pastDaysList);
            return pastDaysList;
        }
        return pastDaysList;
    }

    /**
     * 判断当前时间是否在时间段内
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @author zhangyicheng
     * @date 2020/07/25
     */
    public static boolean belongCalendar(String beginTime, String endTime) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        Calendar date = Calendar.getInstance();
        try {
            date.setTime(df.parse(df.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar begin = Calendar.getInstance();
        try {
            begin.setTime(df.parse(beginTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar end = Calendar.getInstance();
        try {
            end.setTime(df.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.after(begin) && date.before(end);
    }

    /**
     * 获取过去N天内的日期集合
     *
     * @param intervals intervals天内
     * @return 日期数组
     * @author zhangyicheng
     * @date 2020/12/04
     */
    public static ArrayList<String> getLastDays(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>(intervals);
        for (int i = intervals - 1; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - i);
            Date today = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            pastDaysList.add(format.format(today));
        }
        return pastDaysList;
    }

    /**
     * 获取过取N月内的集合
     *
     * @param intervals intervals月内
     * @author zhangyicheng
     * @date 2020/12/04
     */
    public static ArrayList<String> getLastMonths(int intervals) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        ArrayList<String> list = new ArrayList<>(intervals);
        for (int i = 0; i < intervals; i++) {
            c.setTime(new Date());
            c.add(Calendar.MONTH, -i);
            Date m = c.getTime();
            list.add(sdf.format(m));
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * 获取当前月的天数
     *
     * @author zhangyicheng
     * @date 2020/12/04
     */
    public static Integer getDayOfMonth() {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        return aCalendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 返回传入日期的 月
     *
     * @param dateTime Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回传入日期的 日
     *
     * @param dateTime 日期
     * @return 返回日份
     */
    public static int getDay(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回传入日期的 时
     *
     * @param dateTime 日期
     * @return 返回小时
     */
    public static int getHour(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回传入日期的 分
     *
     * @param dateTime 日期
     * @return 返回分钟
     */
    public static int getMinute(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回传入日期的 秒
     *
     * @param dateTime 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 返回传入日期的毫秒
     *
     * @param dateTime 日期
     * @return 返回毫
     */
    public static long getMillis(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        return calendar.getTimeInMillis();
    }

    /**
     * 判断两个日期天数
     *
     * @author zhangyicheng
     * @date 2022/03/23
     */
    public static Long betweenDays(String sDay, String eDay) {

        // 自定义时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 获取日历对象
        Calendar calendarA = Calendar.getInstance();
        Calendar calendarB = Calendar.getInstance();

        Date dateA = null;
        Date dateB = null;

        try {
            // 字符串转Date
            dateA = simpleDateFormat.parse(sDay);
            dateB = simpleDateFormat.parse(eDay);
            // 设置日历
            calendarA.setTime(dateA);
            calendarB.setTime(dateB);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long timeA = calendarA.getTimeInMillis();
        long timeB = calendarB.getTimeInMillis();

        return (timeB - timeA) / (1000 * 3600 * 24);
    }

}
