package com.yhb.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/8 10:55
 * @Description:
 */
public class DateUtil {
    public static final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter dayTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DateUtil() {
    }

    public static Date parseDayTime(String dayTimeStr) {
        return parseDayTime(dayTimeStr, dayTimeFormatter);
    }

    public static Date parseDayTime(String dayTimeStr, DateTimeFormatter formatter) {
        LocalDateTime localDateTime = LocalDateTime.parse(dayTimeStr, formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date plusSecond(Date date, int second) {
        return new Date(date.getTime() + (long)(1000 * second));
    }

    public static Date minusSecond(Date date, int second) {
        return new Date(date.getTime() - (long)(1000 * second));
    }

    public static Date plusDay(Date date, int days) {
        return new Date(date.getTime() + (long)(86400000 * days));
    }

    public static Date minusDay(Date date, int days) {
        return new Date(date.getTime() - (long)(86400000 * days));
    }

    public static long intervalSecond(Date begin, Date end) {
        return Math.abs((end.getTime() - begin.getTime()) / 1000L);
    }

    public static long intervalMinute(Date begin, Date end) {
        return Math.abs((end.getTime() - begin.getTime()) / 3600000L);
    }

    public static long intervalDay(Date begin, Date end) {
        return Math.abs((end.getTime() - begin.getTime()) / 86400000L);
    }

    public static Date now() {
        return new Date();
    }

    public static Date today() {
        LocalDate localDate = LocalDate.now();
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date yesterday() {
        return minusDay(today(), 1);
    }

    public static void main(String[] args) {
    }
}
