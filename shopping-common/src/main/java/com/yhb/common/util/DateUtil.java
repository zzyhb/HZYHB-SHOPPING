package com.yhb.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: shenjiayu
 * Date: 13-9-18
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public final class DateUtil {

    private static Log log = LogFactory.getLog(DateUtil.class);
    private static final String TIME_PATTERN = "HH:mm";
    public static final String MD_FORMAT = "MM-dd";
    public static final String YMD_HMS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String String_YMDHMS_FORMAT = "yyyyMMddHHmmss";
    public static final String YMD_FORMAT = "yyyy-MM-dd";
    public static final String YM_FORMAT = "yyyy-MM";
    public static final String Y_FORMAT = "yyyy";
    public static final String FISCAL_YEAR_START_POSTFIX = "-04-01";
    public static final String FISCAL_YEAR_QUARTER_2_START_POSTFIX = "-07-01";
    public static final String FISCAL_YEAR_QUARTER_3_START_POSTFIX = "-10-01";
    public static final String FISCAL_YEAR_QUARTER_4_START_POSTFIX = "-01-01";
    public static final String FISCAL_YEAR_END_POSTFIX = "-03-31";

    public static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private DateUtil() {
    }

    /**
     * Return default datePattern (MM/dd/yyyy)
     *
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        Locale locale = LocaleContextHolder.getLocale();
        String defaultDatePattern;
        try {
            defaultDatePattern = ResourceBundle.getBundle("ApplicationResources",
                    locale).getString("date.format");
        } catch (MissingResourceException mse) {
            defaultDatePattern = "MM/dd/yyyy HH:mm:ss";
        }
        if (defaultDatePattern == null || defaultDatePattern.isEmpty()) {
            defaultDatePattern = "MM/dd/yyyy HH:mm:ss";
        }

        return defaultDatePattern;
    }

    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }

    /**
     * This method attempts to convert an Oracle-formatted date in the form
     * dd-MMM-yyyy to mm/dd/yyyy.
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date/time in the
     * format you specify on input
     *
     * @param aMask   the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @throws ParseException when String doesn't match the expected format
     * @see SimpleDateFormat
     */
    public static Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '"
                    + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            // log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format: MM/dd/yyyy HH:MM
     * a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(TIME_PATTERN, theTime);
    }

    public static String getDateString(Date theTime){
        return  getDateTime(YMD_FORMAT,theTime);
    }
    /**
     * This method returns the current date in the format: MM/dd/yyyy
     *
     * @return the current date
     * @throws ParseException when String doesn't match the expected format
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.warn("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based on the
     * System Property 'dateFormat' in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(String aMask, Date aDate) {
        return getDateTime(aMask, aDate);
    }

    /**
     * This method generates a string representation of a date based on the
     * System Property 'dateFormat' in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(final String strDate)
            throws ParseException {
        return convertStringToDate(getDatePattern(), strDate);
    }

    public static String formatDateWithChinaDf(Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        }
    }

    public static String getDispalyDateTime(Date date) {
        String retString = "";
        if (date == null) {
            return retString;
        }

        Long m = ((new Date()).getTime() - date.getTime()) / 1000;
        Long oneHour = 60 * 60L;
        Long oneDay = 24 * oneHour;
        Long oneWeek = 7 * oneDay;
        Long fourWeek = oneWeek * 4;

        if (m <= 60) {
            retString = m + "秒前";
        } else if (m < oneHour) {
            retString = m / 60 + "分钟前";
        } else if (m < oneDay) {
            retString = m / oneHour + "小时前";
        } else if (m < oneWeek) {
            retString = m / oneDay + "天前";
        } else if (m < fourWeek) {
            retString = m / oneWeek + "周前";
        } else {
            retString = DateUtil.getDateTime("yy-MM-dd", date);
        }
        return retString;
    }

    public static Date setBeginOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        date = cal.getTime();
        return date;
    }

    public static Date setEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        date = cal.getTime();
        return date;
    }

    public static long nDaysBetweenTwoDate(Date beginDate, Date endDate)
            throws ParseException {
        long day = 0;
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    public static Date nDaysAfterOneDate(Date basicDate, long n) {
        long time = n * (24 * 60 * 60 * 1000);
        long nDay = basicDate.getTime() + time;
        Date newDate = new Date(nDay);
        return newDate;
    }

    /**
     * 计算两个日子之间的工作日时间
     *
     * @param startDate
     * @param endDate
     * @return
     */

    public static int getWorkDays(Date startDate, Date endDate) {
        int result = 0;
        Calendar cal_start = Calendar.getInstance();
        Calendar cal_end = Calendar.getInstance();
        cal_start.setTime(startDate);
        cal_end.setTime(endDate);
        while (cal_start.compareTo(cal_end) <= 0) {
            if (cal_start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal_start.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
                result++;
            }
            cal_start.add(Calendar.DATE, 1);
        }

        return result;
    }

    /**
     * 上个月的第一天
     *
     * @return
     */
    public static Date getLastMonthFirstDay() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(getLastMonthLastDay());
        ;
        //上个月的第一天
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    /**
     * 上个月的最后一天
     *
     * @return
     */
    public static Date getLastMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //日期减一，获得上个月的最后一天
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 当前财年开始日期
     *
     * @return
     * @throws ParseException
     */
    public static Date getCurrentFiscalYearStartDate() throws ParseException {
        /**
         * 当前年的4月1日,财年开始的日期
         */
        Date fiscalYearSeparator = new Date();
        fiscalYearSeparator.setMonth(3);
        fiscalYearSeparator.setDate(1);
        Date today = new Date();
        Integer currentYear = Integer.valueOf(convertDateToString("yyyy", today));
        if (nDaysBetweenTwoDate(today, fiscalYearSeparator) < 0) {
            /**
             * 当前时间比4月份大
             */
            return DateUtil.convertStringToDate(DateUtil.YMD_FORMAT, currentYear + FISCAL_YEAR_START_POSTFIX);
        } else {
            return DateUtil.convertStringToDate(DateUtil.YMD_FORMAT, (currentYear - 1) + FISCAL_YEAR_START_POSTFIX);
        }
    }

    /**
     * 当前财年结束日期
     *
     * @return
     * @throws ParseException
     */
    public static Date getCurrentFiscalYearEndDate() throws ParseException {
        /**
         * 当前年的4月1日,财年开始的日期
         */
        Date fiscalYearSeparator = new Date();
        fiscalYearSeparator.setMonth(3);
        fiscalYearSeparator.setDate(1);
        Date today = new Date();
        Integer currentYear = Integer.valueOf(convertDateToString("yyyy", today));
        if (nDaysBetweenTwoDate(today, fiscalYearSeparator) < 0) {
            /**
             * 当前时间比4月份大
             */
            return DateUtil.convertStringToDate(DateUtil.YMD_FORMAT, (currentYear + 1) + FISCAL_YEAR_END_POSTFIX);
        } else {
            return DateUtil.convertStringToDate(DateUtil.YMD_FORMAT, currentYear + FISCAL_YEAR_END_POSTFIX);
        }
    }

    /**
     * 传入一个年份获取财年开始和结束日期
     * @param
     * @param
     * @return
     */
    public static Map<String,Date> getCurrentFiscalYearStartAndEnd(Date currentTime) throws ParseException {
        Map<String,Date> timeMap = new HashMap<>();
        //String year = currentYear + FISCAL_YEAR_START_POSTFIX;
        //Date startDate = DateUtil.convertStringToDate(DateUtil.YMD_FORMAT,year);//17 4 1
        //timeMap.put("startTime",startDate);
        //Date fiscalYearSeparator = DateUtil.convertStringToDate(DateUtil.YMD_FORMAT,currentYear + FISCAL_YEAR_START_POSTFIX);
        Date date = new Date(currentTime.getTime());
        date.setMonth(3);
        date.setDate(1);
        Integer current = Integer.valueOf(convertDateToString("yyyy", currentTime));
        if (nDaysBetweenTwoDate(currentTime, date) < 0) {
            timeMap.put("endTime",DateUtil.convertStringToDate(DateUtil.YMD_FORMAT, (current + 1) + FISCAL_YEAR_END_POSTFIX));
            timeMap.put("startTime",DateUtil.convertStringToDate(DateUtil.YMD_FORMAT, current + FISCAL_YEAR_START_POSTFIX));
        } else {
            timeMap.put("startTime",DateUtil.convertStringToDate(DateUtil.YMD_FORMAT, (current - 1) + FISCAL_YEAR_START_POSTFIX));
            timeMap.put("endTime",DateUtil.convertStringToDate(DateUtil.YMD_FORMAT, current + FISCAL_YEAR_END_POSTFIX));
        }
        return timeMap;
    }

    public static String transferTwoDateToMapKey(Date startDate, Date endDate) {
        return convertDateToString(DateUtil.YMD_FORMAT, startDate)
                + "~"
                + convertDateToString(DateUtil.YMD_FORMAT, endDate);
    }


    public static Date getEndDateTime(String date){
        if (StringUtils.isBlank(date)) {
            date = DateUtil.convertDateToString(DateUtil.YMD_FORMAT, new Date());
        }
        Date toDate = null;
        try {
            toDate = DateUtil.convertStringToDate(DateUtil.YMD_FORMAT, date);
            toDate = DateUtil.setEndOfDay(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return toDate;
    }

    public static Date getBeginDateTime(String date){
        if (StringUtils.isBlank(date)) {
            date = DateUtil.convertDateToString(DateUtil.YMD_FORMAT, new Date());
        }
        Date fromDate = null;
        try {
            fromDate = DateUtil.convertStringToDate(DateUtil.YMD_FORMAT, date);
            fromDate = DateUtil.setBeginOfDay(fromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fromDate;
    }

    /**
     * 获取 起止时间中的天数，判断逻辑
     * 开始时间大于当前 日期 返回0；
     * 结束日期大于开始日期 返回0
     * 结束日期大于当前日期结束日期等于当前日期
     * @return
     */
    public static Long getDays(Date startDate,Date endDate){
        Long days = 0L;
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        now.setTime(new Date());
        if(now.before(end)){
            end=now;
        }
        if(now.before(start)){
            days= 0L;
        }
        if(end.before(start)){
            days=  0L;
        }
        if(start.before(end)){
            days=  (end.getTimeInMillis()-start.getTimeInMillis())/(1000*3600*24);
        }
        return days;
    }

    /**
     * 某天的前index天的日期
     * 前n天 -index，后n天 index
     * @return
     */
    public static String getDateByInterval(Date date,int index){
        Date dat = null;
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DATE, index);
        dat = cd.getTime();
        return getDateString(dat);
    }

    /**
     * 获取周一日期
     * @return
     */
    public static String getSpecifiedWeekMonday(Date specifiedDay){
        Calendar cal=new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(specifiedDay);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date first=cal.getTime();
        return DateUtil.convertDateToString(DateUtil.YMD_FORMAT,first);
    }

    public static String getSpecifiedWeekSunday(Date specifiedDay){
        Calendar cal=new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(specifiedDay);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek()+6);
        Date first=cal.getTime();
        return DateUtil.convertDateToString(DateUtil.YMD_FORMAT,first);
    }

    public static String getWeekMonday(){
        Calendar cal=new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date first=cal.getTime();
        return DateUtil.convertDateToString(DateUtil.YMD_FORMAT,first);
    }

    /**
     * 获取本周周日
     * @return
     */
    public static String getWeekSunday(){
        Calendar cal=new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek()+6);
        Date first=cal.getTime();
        return DateUtil.convertDateToString(DateUtil.YMD_FORMAT,first);
    }

    /**
     * 获取上周周一日期
     * @return
     */
    public static String getLastWeekMonday(){
        Calendar cal=new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.DATE,-7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date first=cal.getTime();
        return DateUtil.convertDateToString(DateUtil.YMD_FORMAT,first);
    }

    /**
     * 获取上周周日
     * @return
     */
    public static String getLastWeekSunday(){
        Calendar cal=new GregorianCalendar();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.add(Calendar.DATE,-7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date first=cal.getTime();
        return DateUtil.convertDateToString(DateUtil.YMD_FORMAT,first);
    }

    public static int getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH)+1;
        return month;
    }

    public static int getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.YEAR);
        return month;
    }
    /**
     * 获得该月第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        String firstDayOfMonth = DateUtil.getDateTime(DateUtil.YMD_FORMAT,cal.getTime());
        return firstDayOfMonth;
    }

    /**
     * 获得该月最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay-1);
        //格式化日期
        String lastDayOfMonth = DateUtil.getDateTime(DateUtil.YMD_FORMAT,cal.getTime());
        return lastDayOfMonth;
    }


    /**
     * 获取指定月的前一月（年）或后一月（年）
     * @param dateStr
     * @param addYear
     * @param addMonth
     * @param addDate
     * @return 输入的时期格式为yyyy-MM-dd，输出的日期格式为yyyy-MM-dd
     * @throws Exception
     */
    public static String getNextDay(String dateStr,int addYear, int addMonth, int addDate){
        Date sourceDate = null;
        try {
            sourceDate = DateUtil.convertStringToDate(DateUtil.YMD_FORMAT,dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(sourceDate);
        cal.add(Calendar.YEAR,addYear);
        cal.add(Calendar.MONTH, addMonth);
        cal.add(Calendar.DATE, addDate);
        String dateTmp = DateUtil.getDateTime(DateUtil.YMD_FORMAT,cal.getTime());
        return dateTmp;
    }

    public static void main(String[] args) throws Exception{
        String date = DateUtil.convertDateToString(DateUtil.YMD_FORMAT, new Date());
        if(new Date().getHours()<11){
            date = DateUtil.getDateByInterval(new Date(),-1);
        }
        System.out.println(date);
    }

    public static String getWeekRangeByYearWeek(Integer year, Integer week) {
        String begin ="" ;
        String end ="" ;
        if(year != null&& week != null){
            begin = getFirstDayOfWeek(year, week);
            end = getLastDayOfWeek(year, week);
            return begin.substring(5,10)+"~"+end.substring(5,10);
        } else {
            return "";
        }
    }

    public static String getFirstDayOfWeek(int year, int week) {

        Calendar firDay = Calendar.getInstance();

        // 先滚动到该年
        firDay.set(Calendar.YEAR, year);

        // 滚动到周
        firDay.set(Calendar.WEEK_OF_YEAR, week);

        // 得到该周第一天
        firDay.set(Calendar.DAY_OF_WEEK, 2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDay = sdf.format(firDay.getTime());

        return firstDay;
    }

    public static String getLastDayOfWeek(int year,int week){

        Calendar lasDay = Calendar.getInstance();

        // 先滚动到该年
        lasDay.set(Calendar.YEAR, year);

        // 滚动到周
        lasDay.set(Calendar.WEEK_OF_YEAR, week);

        // 得到该周第一天
        lasDay.set(Calendar.DAY_OF_WEEK, 2);

        // 得到该周最后一天
        lasDay.add(Calendar.DAY_OF_WEEK, 6);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDay = sdf.format(lasDay.getTime());

        return lastDay;
    }

    public static String getCronByDate(final Date date){
        return DateUtil.getDateTime(DateUtil.CRON_DATE_FORMAT, date);
    }

    public static String getCronByDateStr(final String dateStr){
        String cron = "";
        try {
            cron = DateUtil.getDateTime(DateUtil.CRON_DATE_FORMAT, convertStringToDate(DateUtil.YMD_HMS_FORMAT,dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cron;
    }

    public static Date getDateByCron(final String cron){
        Date cronDate = null;
        try {
            cronDate = DateUtil.convertStringToDate(DateUtil.CRON_DATE_FORMAT, cron);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cronDate;
    }

    public static String getExecuteTime(Date endTime, Long interval){
        String executeTime ="";
        String validExecuteTime ="";
        if(endTime!=null){
            for(int index=0; index<=interval; index++){
                executeTime += DateUtil.getCronByDateStr(DateUtil.getDateByInterval(endTime,-index) + " 10:00:00")+"," +
                        DateUtil.getCronByDateStr(DateUtil.getDateByInterval(endTime,-index) + " 14:00:00")+",";
            }
            for(String time : executeTime.split(",")){
                if(DateUtil.getDateByCron(time).before(new Date())){
                    continue;
                } else {
                    validExecuteTime +="," +time;
                }
            }
            if(StringUtils.isNotBlank(validExecuteTime)){
                validExecuteTime = validExecuteTime.substring(1);
            }
        }
        return validExecuteTime;
    }

    public static String getExecuteTime(Date endTime, int timeBegin, int timeAfter){
        String executeTime ="";
        String validExecuteTime ="";
        if(endTime!=null){
            for(int index=timeBegin; index<=timeAfter; index++){
                executeTime += DateUtil.getCronByDateStr(DateUtil.getDateByInterval(endTime,index) + " 10:00:00")+"," +
                        DateUtil.getCronByDateStr(DateUtil.getDateByInterval(endTime,index) + " 14:00:00")+",";
            }
            for(String time : executeTime.split(",")){
                if(DateUtil.getDateByCron(time).before(new Date())){
                    continue;
                } else {
                    validExecuteTime +="," +time;
                }
            }
            if(StringUtils.isNotBlank(validExecuteTime)){
                validExecuteTime = validExecuteTime.substring(1);
            }
        }
        return validExecuteTime;
    }
}