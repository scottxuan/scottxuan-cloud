package scottxuan.cloud.base.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author : scottxuan
 */
public class DateUtils {
    private static final String SIMPLE_DATE = "yyyy-MM-dd";
    private static final String SIMPLE_TIME = "HH:mm:ss";
    private static final String SIMPLE_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获得当前时间的yyyy-MM-dd格式字符串
     * @return String
     */
    public static String getCurrent(String format){
        DateTimeFormatter  df = DateTimeFormatter.ofPattern(format);
        LocalDate today = LocalDate.now();
        return today.format(df);
    }

    /**
     * 获得当前时间的yyyy-MM-dd HH:mm:ss格式字符串
     * @return String
     */
    public static String getCurrentDateTime(){
        DateTimeFormatter  df = DateTimeFormatter.ofPattern(SIMPLE_DATE_TIME);
        LocalDate today = LocalDate.now();
        return today.format(df);
    }

    /**
     * 获得当前时间的yyyy-MM-dd格式字符串
     * @return String
     */
    public static String getCurrentDate(){
        DateTimeFormatter  df = DateTimeFormatter.ofPattern(SIMPLE_DATE);
        LocalDate today = LocalDate.now();
        return today.format(df);
    }

    /**
     * 获得当前时间的HH:mm:ss格式字符串
     * @return String
     */
    public static String getCurrentTime() {
        DateTimeFormatter  df = DateTimeFormatter.ofPattern(SIMPLE_TIME);
        LocalDate today = LocalDate.now();
        return today.format(df);
    }

    /**
     * Date转化为yyyy-MM-dd HH:mm:ss格式字符串
     * @param date
     * @return
     */
    public static String toSimpleDateTime(Date date){
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE_TIME);
        return format.format(date);
    }

    /**
     * Date转化为yyyy-MM-dd格式字符串
     * @param date
     * @return
     */
    public static String toSimpleDate(Date date){
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(SIMPLE_DATE);
        return format.format(date);
    }

    /**
     * Date转化为HH:mm:ss格式字符串
     * @param date
     * @return
     */
    public static String toSimpleTime(Date date){
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(SIMPLE_TIME);
        return format.format(date);
    }

    /**
     * LocalDate转化为yyyy-MM-dd HH:mm:ss格式字符串
     * @param localDate
     * @return
     */
    public static String toSimpleDateTime(LocalDate localDate){
        if (localDate == null) {
            return "";
        }
        DateTimeFormatter  df = DateTimeFormatter.ofPattern(SIMPLE_DATE_TIME);
        return localDate.format(df);
    }

    /**
     * LocalDate转化为yyyy-MM-dd格式字符串
     * @param localDate
     * @return
     */
    public static String toSimpleDate(LocalDate localDate){
        if (localDate == null) {
            return "";
        }
        DateTimeFormatter  df = DateTimeFormatter.ofPattern(SIMPLE_DATE);
        return localDate.format(df);
    }

    /**
     * LocalDate转化为HH:mm:ss格式字符串
     * @param localDate
     * @return
     */
    public static String toSimpleTime(LocalDate localDate){
        if (localDate == null) {
            return "";
        }
        DateTimeFormatter  df = DateTimeFormatter.ofPattern(SIMPLE_TIME);
        return localDate.format(df);
    }

    /**
     * LocalDate转化为指定格式字符串
     * @param localDate
     * @param format
     * @return
     */
    public static String format(LocalDate localDate, String format){
        if (localDate == null) {
            return "";
        }
        DateTimeFormatter  df = DateTimeFormatter.ofPattern(format);
        return localDate.format(df);
    }

    /**
     * Date转化为指定格式字符串
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format){
        if (date == null) {
            return "";
        }
        SimpleDateFormat  sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
