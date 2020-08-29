package com.scottxuan.base.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author : scottxuan
 */
public class DateUtils {
    public static final String SIMPLE_DATE = "yyyy-MM-dd";
    public static final String SIMPLE_TIME = "HH:mm:ss";
    public static final String SIMPLE_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * LocalDateTime转化为指定格式字符串
     *
     * @param localDateTime
     * @param format
     * @return
     */
    public static String format(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) {
            return "";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(df);
    }

    /**
     * LocalDateTime转化为yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(SIMPLE_DATE_TIME);
        return localDateTime.format(df);
    }

    /**
     * LocalDateTime转化为yyyy-MM-dd
     *
     * @param localDateTime
     * @return
     */
    public static String formatLocalDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(SIMPLE_DATE);
        return localDateTime.format(df);
    }

    /**
     * LocalDateTime转化为HH:mm:ss
     *
     * @param localDateTime
     * @return
     */
    public static String formatLocalTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(SIMPLE_TIME);
        return localDateTime.format(df);
    }

    /**
     * Date转化为指定格式字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Date转化为指定格式字符串
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_TIME);
        return sdf.format(date);
    }

    /**
     * Date转化为指定格式字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE);
        return sdf.format(date);
    }

    /**
     * Date转化为指定格式字符串
     *
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_TIME);
        return sdf.format(date);
    }

    /**
     * String 解析为 Date
     *
     * @param str
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parse(String str, String format) throws ParseException {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }

    /**
     * String 解析为 Date
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date parseDateTime(String str) throws ParseException {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_TIME);
        return sdf.parse(str);
    }

    /**
     * String 解析为 Date
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String str) throws ParseException {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE);
        return sdf.parse(str);
    }

    /**
     * String 解析为 Date
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date parseTime(String str) throws ParseException {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_TIME);
        return sdf.parse(str);
    }

    /**
     * String 解析为 LocalDateTime
     *
     * @param str
     * @return
     */
    public static LocalDateTime parseLocal(String str, String format) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(str, dtf);
    }

    /**
     * String 解析为 LocalDateTime
     *
     * @param str
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(SIMPLE_DATE_TIME);
        return LocalDateTime.parse(str, dtf);
    }

    /**
     * String 解析为 LocalDate
     *
     * @param str
     * @return
     */
    public static LocalDate parseLocalDate(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(SIMPLE_DATE);
        return LocalDate.parse(str, dtf);
    }

    /**
     * String 解析为 LocalTime
     *
     * @param str
     * @return
     */
    public static LocalTime parseLocalTime(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(SIMPLE_TIME);
        return LocalTime.parse(str, dtf);
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime){
        if(null == localDateTime) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDate2Date(LocalDate localDate){
        if(null == localDate) {
            return null;
        }
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime date2LocalDateTime(Date date){
        if(null == date) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate date2LocalDate(Date date){
        if(null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
