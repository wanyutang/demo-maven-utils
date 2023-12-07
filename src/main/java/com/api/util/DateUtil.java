package com.api.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {

    public static final String BASICE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String HR_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYMMDDHHMMSS = "yyMMddHHmmss";
    public static final String YYYY_MM_DD = "yyyy/MM/dd";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY = "yyyy";
    public static final String M = "M";
    public static final String HHMMSS = "HHmmss";
    public static final String YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";
    public static final String START_TIME = "00:00:00";
    public static final String END_TIME = "23:59:59";
    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmss.SSS";
    public static final String PUSH_YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String HHMM = "HHmm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYMMDD = "yyMMdd";
    public static final String YYYMMDD = "yyyMMdd";
    public static final String YYYMMDDHHMMSS = "yyyMMddHHmmss";
    public static final String MMDD = "MMdd";
    public static final String YYY_MM_DD = "yyy/MM/dd";
    public static final String YYMMDDHH = "yyMMddHH";


    public static String formatDate(String format) {
        return formatDate(LocalDateTime.now(), format);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) return null;

        return formatDate(LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault()), format);
    }

    public static String formatDateString(LocalDateTime dateTime, String format) {
        if(dateTime == null) return "";
        return formatDate(dateTime,format);
    }

    public static String formatDate(LocalDateTime dateTime, String format) {
        if (dateTime == null) return null;

        return DateTimeFormatter.ofPattern(format).format(dateTime);
    }
    public static String formatLocalDate(LocalDate localDate, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDate);
    }


    public static Date formatDateString(String date, String format) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }




    public static Date formatString(String date, String format) {
        LocalDateTime localDateTime = LocalDateTime.parse(date,
                DateTimeFormatter.ofPattern(format));
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String getStartDateTime(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDateTime today_start = LocalDateTime.of(localDate, LocalTime.MIN);

        return today_start.format(DateTimeFormatter.ofPattern(DateUtil.BASICE_FORMAT));
    }

    public static String getEndDateTime(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDateTime today_start = LocalDateTime.of(localDate, LocalTime.MAX);

        return today_start.format(DateTimeFormatter.ofPattern(DateUtil.BASICE_FORMAT));
    }

    /**
     * 日期時間設為 23:59:59
     */
    public static Date getTheEndOfTheDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return new Date(cal.getTimeInMillis() - 1);
    }

    /**
     * 日期時間設為 00:00:00
     */
    public static Date getTheStartOfTheDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 日期＋時間
     */
    public static Date getDateAddTime(String date, String time) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        return DateUtil.formatString(date + " " + time, DateUtil.BASICE_FORMAT);
    }

    /**
     * 取得某個時間點的前後 (ex. 10天前、2個月後)
     *
     * @param field  ex. <code>Calendar.DATE</code> 、 <code>Calendar.MONTH</code>
     * @param amount ex. -10 、 2
     */
    public static Date getDateByAddOrSubstract(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 取得該年 某月份天數
     *
     * @param year  ex. 2020
     * @param month ex. 2
     * @return ex. 29
     */
    public static String getDaysOfMonth(Integer year, Integer month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        return String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * 取得當前月份 1~12
     * @return
     */
    public static int getCurrentMonth() {
        return LocalDateTime.now().getMonthValue();
    }


    public static Integer getCurrentAgeByBirthday(String birthdayDate) throws Exception {
        return getCurrentAgeByBirthday(formatDateString(birthdayDate, YYYYMMDD));
    }

    public static Integer getCurrentAgeByBirthday(Date dateOfBirth) throws Exception {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new Exception("Error: birthDate after today");
        }
        int todayYear = today.get(Calendar.YEAR);
        int birthDateYear = birthDate.get(Calendar.YEAR);
        int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
        int birthDateDayOfYear = birthDate.get(Calendar.DAY_OF_YEAR);
        int todayMonth = today.get(Calendar.MONTH);
        int birthDateMonth = birthDate.get(Calendar.MONTH);
        int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
        int birthDateDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
        int age = todayYear - birthDateYear;

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth)){
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        } else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth)){
            age--;
        }
        return age;
    }

    public static boolean isBetween(Date date, Date min, Date max) {
        max = getDateByAddOrSubstract(max, Calendar.DATE, 1);
        if (min == null) {
            return date.compareTo(max) < 0;
        } else {
            return min.compareTo(date) * date.compareTo(max) > 0;
        }
    }

    public static boolean isBetweenInclusive(Date date, Date min, Date max) {
        max = getDateByAddOrSubstract(max, Calendar.DATE, 1);
        if (min == null) {
            return date.compareTo(max) <= 0;
        } else {
            return min.compareTo(date) * date.compareTo(max) >= 0;
        }
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return StringUtils.equals(formatDate(date1, YYYYMMDD), formatDate(date2, YYYYMMDD));
    }

    /**
     * 比較當前時間是否在日期加上years年之內
     * <pre>
     * 將date1 加上 years（可為負數時間）
     * 與當前時間做比對
     *
     * @param date1 初始日期
     * @param years 年份
     * @author Spring
     * @return 今日時間日否在期間之內
     * </pre>
     */
    public static boolean checkDateInYears(Date date1, int years) {
        Calendar plusYears = Calendar.getInstance();
        plusYears.setTime(getTheEndOfTheDate(date1));
        plusYears.add(Calendar.YEAR, years);
        Date afterPlusDate = plusYears.getTime();
        Date today = new Date();
        return (today.compareTo(afterPlusDate) < 0);
    }


    /**
     * 取現在民國年月日 yyyMMdd
     */
    public static String getMinguoDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return MinguoDate.from(localDate).format(DateTimeFormatter.ofPattern(YYYMMDD));
    }

    /**
     * LocalDateTime 取民國年字串格式
     */
    public static String formatMinguoString(LocalDateTime date, String format) {
        if(date == null) return "";
        return MinguoDate.from(date).format(DateTimeFormatter.ofPattern(format));
    }


    public static String getMinguoDate(LocalDateTime time) {
        return MinguoDate.from(time).format(DateTimeFormatter.ofPattern(YYYMMDD));
    }

    /**
     *取現在民國年月日時分秒 yyyMMddHHsmmss
     */
    public static String getMinguoTime() {
        return getMinguoDate() + DateUtil.formatDate(HHMMSS);
    }

    /**
     * Transfer minguo date to AD date.
     * 民國年 yyyMMdd 轉 西元年 yyyyMMdd
     *
     * @param dateString the String dateString
     * @return the string
     */
    public static String transferMinguoDateToADDate(String dateString) {
        Chronology chrono = MinguoChronology.INSTANCE;
        DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient()
                .appendPattern("yyyMMdd")
                .toFormatter()
                .withChronology(chrono)
                .withDecimalStyle(DecimalStyle.of(Locale.getDefault()));

        ChronoLocalDate chDate = chrono.date(df.parse(dateString));
        return LocalDate.from(chDate).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 民國年(ROC)轉西元年(AD)
     * 例: 0112/03/22
     */
    public static LocalDate ROCFormatterToADLocalDate(String dateString, String format) {
        Chronology chrono = MinguoChronology.INSTANCE;
        DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient()
                .appendPattern(format)
                .toFormatter()
                .withChronology(chrono)
                .withDecimalStyle(DecimalStyle.of(Locale.getDefault()));
        ChronoLocalDate chDate = chrono.date(df.parse(dateString));
        return LocalDate.from(chDate);
    }

    /**
     * 民國年(ROC)轉 LocalDate
     * 例: 0112/03/22
     */
    public static LocalDate ROCFormatterToADDate(String dateString) {
        return ROCFormatterToADDate(dateString, "yyy/MM/dd");
    }

    public static LocalDate ROCFormatterToADDate(String dateString, String format) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * LocalDate convert Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     *  Date convert LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     *  Date convert LocalDate
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    public static Timestamp getCurrentTimestamp() {
        Instant currentInstant = Instant.now();
        return Timestamp.from(currentInstant);
    }

    public static long getDiffTimestamp(Timestamp currentTime, Timestamp oldTime) {
        return currentTime.getTime() - oldTime.getTime();
    }
    
}
