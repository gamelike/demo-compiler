package org.example.utils;

import org.example.enums.RelativeTimeType;
import org.example.enums.RelativeTimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * data utils.
 **/
@Slf4j
public class DateUtils {

    public static final int DEFAULT = -6;

    public static String DateToString(Date date) {
        return DateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date StringToDate(String date) {
        return StringToDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String DateToString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date StringToDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date curDate = null;
        try {
            curDate = dateFormat.parse(date);
        } catch (ParseException e) {
            log.error("转换日期类型错误");
        }
        return curDate;
    }

    public static String localDateTime2String(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime2String(date, formatter);
    }

    public static String localDateTime2String(LocalDateTime date, DateTimeFormatter formatter) {
        return date.format(formatter);
    }

    public static LocalDateTime string2LocalDateTime(String date) {
        Instant instant = StringToDate(date).toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime string2LocalDateTime(String date, String format) {
        Instant instant = StringToDate(date, format).toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date addDay(Date curDate, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static DateHistogramInterval dataHistogram(String unit, Integer duration) {
        if (unit == null) return DateHistogramInterval.MONTH;
        switch (unit) {
            case "second":
                return DateHistogramInterval.seconds(duration);
            case "minute":
                return DateHistogramInterval.minutes(duration);
            case "hour":
                return DateHistogramInterval.hours(duration);
            case "day":
                return DateHistogramInterval.days(duration);
            case "week":
                return DateHistogramInterval.weeks(duration);
            default:
                return DateHistogramInterval.MONTH;
        }
    }

    public static Map<String, String> getStartAndEnd(int field, int amount) {
        Map<String, String> data = new HashMap<>();
        Date endDate = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(endDate);
        cal.add(field, -amount);
        Date startDate = cal.getTime();
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getTodayStartAndEnd() {
        Map<String, String> data = new HashMap<>();
        Date startDate = getDayBegin();
        Date endDate = getDayEnd();
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getYesStartAndEnd() {
        Map<String, String> data = new HashMap<>();
        Date startDate = getBeginDayOfYesterday();
        Date endDate = getEndDayOfYesterDay();
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getWeekStartAndEnd() {
        Map<String, String> data = new HashMap<>();
        Date startDate = getBeginDayOfWeek();
        Date endDate = getEndDayOfWeek();
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getLastWeekStartAndEnd() {
        Map<String, String> data = new HashMap<>();
        Date startDate = getBeginDayOfLastWeek();
        Date endDate = getEndDayOfLastWeek();
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getMonthStartAndEnd() {
        Map<String, String> data = new HashMap<>();
        Date startDate = getBeginDayOfMonth();
        Date endDate = getEndDayOfMonth();
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getLastMonthStartAndEnd() {
        Map<String, String> data = new HashMap<>();
        Date startDate = getBeginDayOfLastMonth();
        Date endDate = getEndDayOfLastMonth();
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getQuarterStartAndEnd() {
        Map<String, String> data = new HashMap<>();
        Date startDate = getBeginDayOfQuarter();
        Date endDate = getEndDayOfQuarter();
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getYearStartAndEnd() {
        Map<String, String> data = new HashMap<>();
        Date startDate = getBeginDayOfYear();
        Date endDate = getEndDayOfYear();
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getLastYearStartAndEnd() {
        Map<String, String> data = new HashMap<>();
        Date startDate = getBeginDayOfLastYear();
        Date endDate = getEndDayOfLastYear();
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getPassDays(int n) {
        Map<String, String> data = new HashMap<>();
        Date endDate = new Date();
        Date startDate = getFrontDay(endDate, n);
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Map<String, String> getPassMins(int n) {
        Map<String, String> data = new HashMap<>();
        Date endDate = new Date();
        Date startDate = getPassMins(endDate, n);
        String start = DateToString(startDate);
        String end = DateToString(endDate);
        data.put("start", start);
        data.put("end", end);
        return data;
    }

    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        return cal.getTime();
    }

    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(5, -1);
        return cal.getTime();
    }

    public static Date getEndDayOfYesterDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(5, -1);
        return cal.getTime();
    }

    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(5, 1);
        return cal.getTime();
    }

    public static Date getEndDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(5, 1);
        return cal.getTime();
    }

    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayofweek = cal.get(7);
            if (dayofweek == 1) {
                dayofweek += 7;
            }

            cal.add(5, 2 - dayofweek);
            return getDayStartTime(cal.getTime());
        }
    }

    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(7, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayofweek = cal.get(7);
            if (dayofweek == 1) {
                dayofweek += 7;
            }

            cal.add(5, 2 - dayofweek - 7);
            return getDayStartTime(cal.getTime());
        }
    }

    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(7, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    public static Date getBeginDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }

    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }

    public static Date getBeginDayOfQuarter() {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = getNowMonth();
        int month = 0;
        if (1 <= currentMonth && currentMonth <= 3) {
            month = 1;
        } else if (4 <= currentMonth && currentMonth <= 6) {
            month = 4;
        } else if (7 <= currentMonth && currentMonth <= 9) {
            month = 7;
        } else if (10 <= currentMonth && currentMonth <= 12) {
            month = 10;
        }

        calendar.set(getNowYear(), month - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    public static Date getEndDayOfQuarter() {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = getNowMonth();
        int month = 0;
        if (1 <= currentMonth && currentMonth <= 3) {
            month = 3;
        } else if (4 <= currentMonth && currentMonth <= 6) {
            month = 6;
        } else if (7 <= currentMonth && currentMonth <= 9) {
            month = 9;
        } else if (10 <= currentMonth && currentMonth <= 12) {
            month = 12;
        }

        calendar.set(getNowYear(), month - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), month - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(1, getNowYear());
        cal.set(2, 0);
        cal.set(5, 1);
        return getDayStartTime(cal.getTime());
    }

    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(1, getNowYear());
        cal.set(2, 11);
        cal.set(5, 31);
        return getDayEndTime(cal.getTime());
    }

    public static Date getBeginDayOfLastYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(1, getNowYear() - 1);
        cal.set(2, 0);
        cal.set(5, 1);
        return getDayStartTime(cal.getTime());
    }

    public static Date getEndDayOfLastYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(1, getNowYear() - 1);
        cal.set(2, 11);
        cal.set(5, 31);
        return getDayEndTime(cal.getTime());
    }

    public static Date getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }

        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);
        calendar.set(14, 0);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }

        calendar.set(calendar.get(1), calendar.get(2), calendar.get(5), 23, 59, 59);
        calendar.set(14, 999);
        return new Date(calendar.getTimeInMillis());
    }

    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(1);
    }

    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    public static int getDiffDays(Date beginDate, Date endDate) {
        if (beginDate != null && endDate != null) {
            long diff = (endDate.getTime() - beginDate.getTime()) / 86400000L;
            int days = (new Long(diff)).intValue();
            return days;
        } else {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }
    }

    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }

    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        } else if (endDate == null) {
            return beginDate;
        } else {
            return beginDate.after(endDate) ? beginDate : endDate;
        }
    }

    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        } else if (endDate == null) {
            return beginDate;
        } else {
            return beginDate.after(endDate) ? endDate : beginDate;
        }
    }

    public static Date getFirstSeasonDate(Date date) {
        int[] SEASON = new int[]{1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(2)];
        cal.set(2, sean * 3 - 3);
        return cal.getTime();
    }

    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(5, cal.get(5) + i);
        return cal.getTime();
    }

    public static Date getPassMseconds(Date date, int n) {
        long time = date.getTime();
        long beginTime = time - (long) (n * 1000L);
        return new Date(beginTime);
    }

    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(5, cal.get(5) - i);
        return cal.getTime();
    }


    public static List getTimeList(int beginYear, int beginMonth, int k) {
        List list = new ArrayList();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(5);

        for (int i = 1; i < max; i += k) {
            list.add(begincal.getTime());
            begincal.add(5, k);
        }

        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }


    public static Date getPassYears(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1) - n, calendar.get(2), calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13));
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getPassMonths(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2) - n, calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13));
        return new Date(calendar.getTimeInMillis());
    }

    public static Date getPassHours(Date date, int n) {
        long time = date.getTime();
        long beginTime = time - (long) ((long) n * 60 * 60 * 1000);
        return new Date(beginTime);
    }

    public static Date getPassMins(Date date, int n) {
        long time = date.getTime();
        long beginTime = time - (long) ((long) n * 60 * 1000);
        return new Date(beginTime);
    }


    public static Map<String, String> getRelativeTime(String relativeTime) {
        if (StringUtils.isEmpty(relativeTime)) {
            throw new RuntimeException("时间参数异常！");
        } else {
            Map<String, String> dateMap = new HashMap<>();
            if (RelativeTimeType.TODAY.getValue().equals(relativeTime)) {
                dateMap = getTodayStartAndEnd();
            } else if (RelativeTimeType.YESTERDAY.getValue().equals(relativeTime)) {
                dateMap = getYesStartAndEnd();
            } else if (RelativeTimeType.WEEK.getValue().equals(relativeTime)) {
                dateMap = getWeekStartAndEnd();
            } else if (RelativeTimeType.LASTWEEK.getValue().equals(relativeTime)) {
                dateMap = getLastWeekStartAndEnd();
            } else if (RelativeTimeType.MONTH.getValue().equals(relativeTime)) {
                dateMap = getMonthStartAndEnd();
            } else if (RelativeTimeType.LASTMONTH.getValue().equals(relativeTime)) {
                dateMap = getLastMonthStartAndEnd();
            } else if (RelativeTimeType.YEAR.getValue().equals(relativeTime)) {
                dateMap = getYearStartAndEnd();
            } else if (RelativeTimeType.QUARTER.getValue().equals(relativeTime)) {
                dateMap = getQuarterStartAndEnd();
            } else if (RelativeTimeType.LASTONEMIN.getValue().equals(relativeTime)) {
                dateMap = getPassMins(1);
            } else if (RelativeTimeType.LASTTENMIN.getValue().equals(relativeTime)) {
                dateMap = getPassMins(10);
            } else if (RelativeTimeType.LASTTHIRTYMIN.getValue().equals(relativeTime)) {
                dateMap = getPassMins(30);
            } else if (RelativeTimeType.LASTONEHOUR.getValue().equals(relativeTime)) {
                dateMap = getPassMins(60);
            } else if (RelativeTimeType.LASTSEVENTDAY.getValue().equals(relativeTime)) {
                dateMap = getPassDays(7);
            } else if (RelativeTimeType.LASTTHIRTYDAY.getValue().equals(relativeTime)) {
                dateMap = getPassDays(30);
            } else if (RelativeTimeType.PASSDAY.getValue().equals(relativeTime)) {
                dateMap = getPassDays(1);
            } else if (RelativeTimeType.PASSTWODAY.getValue().equals(relativeTime)) {
                dateMap = getPassDays(2);
            }

            return dateMap;
        }
    }

    public static Map<String, String> getRelativeTimeUnit(String relaStartTime, String relaEndTime) {
        Date currentDate = new Date();
        String startTime = getTime(currentDate, relaStartTime);
        String endTime = getTime(currentDate, relaEndTime);
        Map<String, String> dateMap = new HashMap<>();
        if (!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)) {
            dateMap.put("start", startTime);
            dateMap.put("end", endTime);
            return dateMap;
        } else {
            throw new RuntimeException("时间格式有误!");
        }
    }

    public static String getTime(Date date, String timeUnit) {
        String resDateStr = "";

        try {
            String[] split = timeUnit.split("-");
            int timeNum = Integer.parseInt(split[0]);
            Date resDate = new Date();
            if (RelativeTimeUnit.SECONDS.getValue().equals(split[1])) {
                resDate = getPassMseconds(date, timeNum);
            } else if (RelativeTimeUnit.MINUTE.getValue().equals(split[1])) {
                resDate = getPassMins(date, timeNum);
            } else if (RelativeTimeUnit.HOUR.getValue().equals(split[1])) {
                resDate = getPassHours(date, timeNum);
            } else if (RelativeTimeUnit.DAY.getValue().equals(split[1])) {
                resDate = getFrontDay(date, timeNum);
            } else if (RelativeTimeUnit.WEEK.getValue().equals(split[1])) {
                resDate = getFrontDay(date, timeNum * 7);
            } else if (RelativeTimeUnit.MONTH.getValue().equals(split[1])) {
                resDate = getPassMonths(date, timeNum);
            } else if (RelativeTimeUnit.YEAR.getValue().equals(split[1])) {
                resDate = getPassYears(date, timeNum);
            }

            resDateStr = DateToString(resDate);
            return resDateStr;
        } catch (Exception var6) {
            throw new RuntimeException("时间格式有误!");
        }
    }

    public static String timeFormat(String timeStr, String formatStr) {
        SimpleDateFormat sf = new SimpleDateFormat(formatStr);
        Date date = StringToDate(timeStr);
        String resTime = sf.format(date);
        return resTime;
    }

    public static String toDhmsStyle(long allSeconds) {
        String DateTimes = null;
        long days = allSeconds / 86400L;
        long hours = allSeconds % 86400L / 3600L;
        long minutes = allSeconds % 3600L / 60L;
        long seconds = allSeconds % 60L;
        if (days > 0L) {
            DateTimes = days + "天" + hours + "时" + minutes + "分" + seconds + "秒";
        } else if (hours > 0L) {
            DateTimes = hours + "时" + minutes + "分" + seconds + "秒";
        } else if (minutes > 0L) {
            DateTimes = minutes + "分" + seconds + "秒";
        } else {
            DateTimes = seconds + "秒";
        }

        return DateTimes;
    }


    public static Date addMinute(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.MINUTE, val);
        return gc.getTime();
    }

    public static Date subMinute(Date date, int val) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.MINUTE, -val);
        return gc.getTime();
    }


}
