package com.info.date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.*;

/**
 * 功能描述: 日期处理
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/5/31 16:06
 * @Return:
 */
public class DateUtils {

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "MM/dd/yyyy HH:mm";
    /**
     * 时间格式(yyyy-MM-dd HH:mm)
     */
    public final static String DATE_MINUTE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);


    /**
     * 功能描述: 获取服务器当前时间
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/7 18:09
     * @Return:
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 功能描述: 新特性获取指定格式数据
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/16 9:13
     * @Return:
     */
    public static String timeOf() {
        String times = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return times;
    }

    /**
     * 功能描述: 获取时间戳
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/16 9:13
     * @Return:
     */
    public static Long timeStimpmer() {
        String times = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
        System.out.println("时间是：" + times);
        java.time.format.DateTimeFormatter ftf = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(times, ftf);
        Long ls = LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println("时间戳：" + ls);
        return ls;
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date    日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date    日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date  日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date  日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date   日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date  日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }


    /**
     * 功能描述: 将时间装换成指定的格式
     *
     * @param:
     * @return:
     * @auther: 高山溪  By User
     * @date: 2018/10/27 12:44
     */
    public static String dateToStringDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(now());
    }

    /**
     * 功能描述: yyyyMMddHHmmss 无时间格式的时间形式
     *
     * @auther: Gaosx  By User
     * @param:
     * @date: 2018/11/23 21:54
     */
    public static String newDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        return df.format(new Date());
    }

    /**
     * @Author: Gaosx
     * @Description: 从当前时间算起延后多多少天获取其时间
     * @Date: 16:47 2018/8/30
     */
    public static Date expireTime(Integer day) {
        Calendar calendar = Calendar.getInstance();
        //当前时间
        Date now = calendar.getTime();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        // 设置过期时间为180天
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear + day);
        //过期时间
//        Date expireTime = calendar.getTime();
        return calendar.getTime();
//        return expireTime;
    }

    /**
     * 功能描述: 根据天数获取所所有的秒数
     *
     * @auther: Gaosx  By User
     * @param:
     * @date: 2019/3/28 11:33
     */
    public static Integer second(Integer days) {
        return days * 24 * 60 * 60;
    }

    public static Integer minimum(Integer minimum) {
        return minimum * 60;
    }

    /**
     * 返回当前周数1-53
     * <p>
     * 周数的定义参考国际规范ISO8601, https://en.wikipedia.org/wiki/ISO_week_date
     *
     * @return
     */
    public static int getCurrentWeekNum() {
        return LocalDateTime.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }


    /**
     * @Explanation: 某一天是一年中的第几周
     * * @param date
     * @DBTable:
     * @date: 18:31 2018/12/5
     * @anthor:Gaosx
     */
    public static int getWeek(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cal.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * @Explanation: 本月第一天
     * * @param
     * @DBTable:
     * @date: 18:32 2018/12/5
     * @anthor:Gaosx
     */
    public static Date getFirstDayOfMonth() {
        Calendar now = Calendar.getInstance();
//        Date date = new Date(2015 - 1900, 12 - 1, 1);
//        now.setTime(date);
        now.set(Calendar.DATE, now.getActualMinimum(Calendar.DATE));
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();
    }

    /**
     * @Explanation: 本月第一天是 一年中第几周
     * * @param
     * @DBTable:
     * @date: 18:37 2018/12/5
     * @anthor:Gaosx
     */
    public static int getWeeKNum() {
        Date firstDayOfMonth = getFirstDayOfMonth();
        Calendar firstDayOfMonthCal = Calendar.getInstance();
        firstDayOfMonthCal.setFirstDayOfWeek(Calendar.MONDAY);
        firstDayOfMonthCal.setTime(firstDayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("本月第一天" + format.format(firstDayOfMonth));
        return getWeek(format.format(firstDayOfMonth));
    }

    /**
     * 获取所给时间在所在年的周数(1-53)
     * <p>
     * 周数的定义参考国际规范ISO8601, https://en.wikipedia.org/wiki/ISO_week_date
     *
     * @param date 非空
     * @return
     */
    public static int getWeekNum(final Date date) {
        Objects.requireNonNull(date);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return LocalDateTime
                .of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), 12, 0)
                .get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }


    /**
     * 返回当前时间 + days代表的时间
     *
     * @param days
     * @return
     */
    public static Date addDays(final Integer days) {
        if (days == null || days < 0) {
            throw new IllegalArgumentException("days must be greater than 0");
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(now());

        calendar.add(Calendar.DAY_OF_YEAR, days);

        return calendar.getTime();
    }


    /**
     * @Explanation: 获取一天所在周的 第一天
     * * @param args
     * @DBTable:
     * @date: 9:55 2018/12/6
     * @anthor:Gaosx
     */
    public static String getWeekStart(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        //所在周开始日期
     /*  System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        //所在周结束日期
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
*/
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * @Explanation: 某天是周几
     * * @param date
     * @DBTable:
     * @date: 10:24 2018/12/6
     * @anthor:Gaosx *@return  0 是周日 1-6  对应周-到周六
     */
    public static int getDayofweek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * @author: YS
     * @Date:2018/5/16 14:32
     * @param:
     * @explain：给两个时间 算出来中间间隔的 时间是多少
     * @return:
     */
    public static String formatDuring(long mss) {
        long hours = (mss / (60 * 60 * 1000));
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        String shours = (hours > 9) ? (hours + "") : ("0" + hours);
        String sminutes = (minutes > 9) ? (minutes + "") : ("0" + minutes);
        String sseconds = (seconds > 9) ? (seconds + "") : ("0" + seconds);
        logger.debug("处理之前传递过来的时间是mss: {}， hours: {}，minutes: {}，seconds: {}，处理之后shours :{}，sminutes: {}，sseconds: {}， ", mss, hours, minutes, seconds, shours, sminutes, sseconds);
        return shours + ":" + sminutes + ":" + sseconds;
    }

    /**
     * 功能描述:
     *
     * @auther: Gaosx  By User
     * @param:
     * @date: 2019/1/9 20:39
     */
    public static String formatDuring(Date begin, Date end) {
        return formatDuring(end.getTime() - begin.getTime());
    }

    /**
     * 将日期时间字符串根据转换为指定时区的日期时间.
     *
     * @param srcDateTime   待转化的日期时间.
     * @param dstTimeZoneId 目标的时区编号.
     * @return 转化后的日期时间.
     * @see #:string2Timezone(String, String, String, String)
     */
    public static String string2TimezoneDefault(String srcDateTime, String dstTimeZoneId) {
        return string2Timezone("yyyy-MM-dd HH:mm:ss", srcDateTime,
                "yyyy-MM-dd HH:mm:ss", dstTimeZoneId);
    }

    /**
     * 将日期时间字符串根据转换为指定时区的日期时间.
     *
     * @param srcFormater   待转化的日期时间的格式.
     * @param srcDateTime   待转化的日期时间.
     * @param dstFormater   目标的日期时间的格式.
     * @param dstTimeZoneId 目标的时区编号.
     * @return 转化后的日期时间.
     */
    public static String string2Timezone(String srcFormater,
                                         String srcDateTime, String dstFormater, String dstTimeZoneId) {
        if (srcFormater == null || "".equals(srcFormater)) {
            return null;
        }
        if (srcDateTime == null || "".equals(srcDateTime)) {
            return null;
        }
        if (dstFormater == null || "".equals(dstFormater)) {
            return null;
        }
        if (dstTimeZoneId == null || "".equals(dstTimeZoneId)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
        try {
            int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);
            Date d = sdf.parse(srcDateTime);
            long nowTime = d.getTime();
            long newNowTime = nowTime - diffTime;
            d = new Date(newNowTime);
            return date2String(dstFormater, d);
        } catch (ParseException e) {
            return null;
        } finally {
            sdf = null;
        }
    }

    /**
     * 获取系统当前默认时区与指定时区的时间差.(单位:毫秒)
     *
     * @param timeZoneId 时区Id
     * @return 系统当前默认时区与指定时区的时间差.(单位 : 毫秒)
     */
    private static int getDiffTimeZoneRawOffset(String timeZoneId) {
        return TimeZone.getDefault().getRawOffset()
                - TimeZone.getTimeZone(timeZoneId).getRawOffset();
    }

    /**
     * 日期(时间)转化为字符串.
     *
     * @param formater 日期或时间的格式.
     * @param aDate    java.util.Date类的实例.
     * @return 日期转化后的字符串.
     */
    public static String date2String(String formater, Date aDate) {
        if (formater == null || "".equals(formater)) {
            return null;
        }
        if (aDate == null) {
            return null;
        }
        return (new SimpleDateFormat(formater)).format(aDate);
    }

    /**
     * 当前日期(时间)转化为字符串.
     *
     * @param formater 日期或时间的格式.
     * @return 日期转化后的字符串.
     */
    public static String date2String(String formater) {
        return date2String(formater, new Date());
    }


    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 功能描述:
     *
     * @auther: Gaosx  By User
     * @param:
     * @date: 2019/1/9 20:39
     */
    public static void main(String[] args) {
        String nowDateTime = date2String("yyyy-MM-dd HH:mm:ss");
        String s = string2TimezoneDefault(nowDateTime,
                "Asia/Shanghai");
        System.out.println(s);

    }

    /**
     * 计算两个时间差, 以分钟为单位
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 相差的分钟数
     */
    public static Integer durationInMinutes(Date startTime, Date endTime) {
        if (endTime == null ||
                startTime == null ||
                endTime.before(startTime)) {
            throw new IllegalArgumentException("invalid start time and end time, cannot get duration");
        }

        final long startTimeInMs = startTime.getTime();
        final long endTimeInMs = endTime.getTime();

        return Math.toIntExact((endTimeInMs - startTimeInMs) / 1000 / 60);
    }

    /**
     * 功能描述:
     *
     * @auther: Gaosx  By User
     * @param:
     * @date: 2019/1/9 20:40
     */
    public static Date minusDays(int days2Minus) {
        if (days2Minus == 0) {
            return now();
        }
        int value = days2Minus > 0 ? 0 - days2Minus : days2Minus;

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(now());

        calendar.add(Calendar.DAY_OF_YEAR, value);

        return calendar.getTime();
    }

    public static Date addHours(Integer hours) {
        final Calendar calendar = Calendar.getInstance();

        calendar.setTime(now());

        calendar.add(Calendar.HOUR_OF_DAY, hours);

        return calendar.getTime();
    }

    public static Date StringToDate(String dateStr, String mode) {
        Date reDate = now();
        SimpleDateFormat s = new SimpleDateFormat(mode);
        try {
            reDate = s.parse(dateStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reDate;
    }

    //获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //
    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    //返回两个时间相差的毫秒值
    public static long getDValue(String begin, String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//按默认的时间规范进行转换
        long value = 0;
        try {
            value = format.parse(end).getTime() - format.parse(begin).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    // 获得某天最大时间 2017-10-15 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        ;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2017-10-15 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

}
