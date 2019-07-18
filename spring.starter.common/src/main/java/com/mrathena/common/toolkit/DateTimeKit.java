package com.mrathena.common.toolkit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author mrathena on 2018-09-27 14:59:04
 */
public final class DateTimeKit {

	public static void main(String[] args) {
		System.out.println(LocalDate.parse("2018-06-01", FORMATTER_DATE));
		System.out.println(DateTimeKit.parseFromDate("2018-06-01"));
		System.out.println(DateTimeKit.formatToDateTimeMs(DateTimeKit.parse("2018-07-0101999", "yyyy-MM-ddHHSSS")));
	}

	private DateTimeKit() {}

	public static final String PATTERN_YEAR = "yyyy";
	public static final String PATTERN_MONTH = "MM";
	public static final String PATTERN_DAY = "dd";
	public static final String PATTERN_HOUR = "HH";
	public static final String PATTERN_MINUTE = "mm";
	public static final String PATTERN_SECOND = "ss";
	public static final String PATTERN_MILLISECOND = "SSS";

	public static final String PATTERN_TIMESTAMP = "yyyyMMddHHmmss";
	public static final String PATTERN_TIMESTAMP_MS = "yyyyMMddHHmmssSSS";
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_TIME = "HH:mm:ss";
	public static final String PATTERN_TIME_MS = "HH:mm:ss.SSS";
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final DateTimeFormatter FORMATTER_TIMESTAMP = DateTimeFormatter.ofPattern(PATTERN_TIMESTAMP);
	public static final DateTimeFormatter FORMATTER_TIMESTAMP_MS = DateTimeFormatter.ofPattern(PATTERN_TIMESTAMP_MS);
	public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern(PATTERN_DATE);
	public static final DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern(PATTERN_TIME);
	public static final DateTimeFormatter FORMATTER_TIME_MS = DateTimeFormatter.ofPattern(PATTERN_TIME_MS);
	public static final DateTimeFormatter FORMATTER_DATE_TIME = DateTimeFormatter.ofPattern(PATTERN_DATE_TIME);
	public static final DateTimeFormatter FORMATTER_DATE_TIME_MS = DateTimeFormatter.ofPattern(PATTERN_DATE_TIME_MS);

	/**
	 * 纪元date 1970-01-01 08:00:00
	 */
	public static final Date EPOCH = new Date(0);

	/**
	 * 一毫秒=1000000纳秒
	 */
	private static final int NANO_PER_MILLI = 1000000;

	/**
	 * 判断两个日期区间是否有重叠部分
	 */
	public static boolean checkIntervalConflict(LocalDate sDate1, LocalDate eDate1, LocalDate sDate2, LocalDate eDate2) {
		if (sDate1 == null || eDate1 == null || sDate2 == null || eDate2 == null) {
			throw new RuntimeException("比较时间不合法");
		}
		if (sDate1.isAfter(eDate1) || sDate2.isAfter(eDate2)) {
			throw new RuntimeException("比较时间不合法");
		}
		LocalDate minDate = sDate1.isBefore(sDate2) ? sDate1 : sDate2;
		LocalDate maxDate = eDate1.isBefore(eDate2) ? eDate2 : eDate1;
		long days = maxDate.toEpochDay() - minDate.toEpochDay();
		long days1 = eDate1.toEpochDay() - sDate1.toEpochDay();
		long days2 = eDate2.toEpochDay() - sDate2.toEpochDay();
//		System.out.println("最大日期与最小日期天数差:" + days);
//		System.out.println("第一个日期区间天数:" + days1);
//		System.out.println("第二个日期区间天数:" + days2);
//		System.out.println("两个日期区间总天数:" + (days1 + days2));
		return days <= (days1 + days2);
	}

	/**
	 * Date转LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * Date转LocalDate
	 */
	public static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * Date转LocalTime
	 */
	public static LocalTime toLocalTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
	}

	/**
	 * LocalDateTime转Date
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * LocalDate转Date 00:00:00
	 */
	public static Date toDate(LocalDate localDate) {
		return toDate(localDate.atStartOfDay());
	}

	/**
	 * LocalTime转Date 1970-01-01
	 */
	public static Date toDate(LocalTime localTime) {
		return toDate(localTime.atDate(toLocalDate(EPOCH)));
	}

	/**
	 * 使用date的年月日创建一个开始date(如:2018-01-01 00:00:00)
	 */
	public static Date toBeginDate(Date date) {
		return toDate(toLocalDate(date));
	}

	/**
	 * 使用date的年月日创建一个结束date(如:2018-01-31 23:59:59)
	 */
	public static Date toEndDate(Date date) {
		return toDate(toLocalDateTime(date).with(LocalTime.MAX));
	}

	/**
	 * 使用年月日时分秒创建一个新的date
	 */
	public static Date newDateTime(int year, int month, int dayOfMonth, int hour, int minute, int second) {
		return toDate(LocalDateTime.of(year, month, dayOfMonth, hour, minute, second));
	}

	/**
	 * 使用年月日时分秒纳秒(9位数)创建一个新的date
	 */
	public static Date newDateTime(int year, int month, int dayOfMonth, int hour, int minute, int second, int millisecond) {
		return toDate(LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, millisecond * NANO_PER_MILLI));
	}

	/**
	 * 使用年月日创建一个新的date
	 */
	public static Date newDate(int year, int month, int dayOfMonth) {
		return toDate(LocalDate.of(year, month, dayOfMonth));
	}

	/**
	 * 使用时分秒创建一个新的date
	 */
	public static Date newTime(int hour, int minute, int second) {
		return toDate(LocalTime.of(hour, minute, second));
	}

	/**
	 * 使用时分秒毫秒创建一个新的date
	 */
	public static Date newTime(int hour, int minute, int second, int millisecond) {
		return toDate(LocalTime.of(hour, minute, second, millisecond * NANO_PER_MILLI));
	}

	/**
	 * 时间添加年数
	 */
	public static Date plusYears(Date date, int years) {
		return toDate(toLocalDateTime(date).plusYears(years));
	}

	/**
	 * 时间添加月数
	 */
	public static Date plusMonths(Date date, int months) {
		return toDate(toLocalDateTime(date).plusMonths(months));
	}

	/**
	 * 时间添加周数
	 */
	public static Date plusWeeks(Date date, int weeks) {
		return toDate(toLocalDateTime(date).plusWeeks(weeks));
	}

	/**
	 * 时间添加天数
	 */
	public static Date plusDays(Date date, int days) {
		return toDate(toLocalDateTime(date).plusDays(days));
	}

	/**
	 * 时间添加时数
	 */
	public static Date plusHours(Date date, int hours) {
		return toDate(toLocalDateTime(date).plusHours(hours));
	}

	/**
	 * 时间添加分数
	 */
	public static Date plusMinutes(Date date, int minutes) {
		return toDate(toLocalDateTime(date).plusMinutes(minutes));
	}

	/**
	 * 时间添加秒数
	 */
	public static Date plusSeconds(Date date, int seconds) {
		return toDate(toLocalDateTime(date).plusSeconds(seconds));
	}

	/**
	 * 时间添加毫秒数
	 */
	public static Date plusMillis(Date date, int millis) {
		return toDate(toLocalDateTime(date).plusNanos(millis * NANO_PER_MILLI));
	}

	/**
	 * 时间减少年数
	 */
	public static Date minusYears(Date date, int years) {
		return toDate(toLocalDateTime(date).minusYears(years));
	}

	/**
	 * 时间减少月数
	 */
	public static Date minusMonths(Date date, int months) {
		return toDate(toLocalDateTime(date).minusMonths(months));
	}

	/**
	 * 时间减少周数
	 */
	public static Date minusWeeks(Date date, int weeks) {
		return toDate(toLocalDateTime(date).minusWeeks(weeks));
	}

	/**
	 * 时间减少天数
	 */
	public static Date minusDays(Date date, int days) {
		return toDate(toLocalDateTime(date).minusDays(days));
	}

	/**
	 * 时间减少时数
	 */
	public static Date minusHours(Date date, int hours) {
		return toDate(toLocalDateTime(date).minusHours(hours));
	}

	/**
	 * 时间减少分数
	 */
	public static Date minusMinutes(Date date, int minutes) {
		return toDate(toLocalDateTime(date).minusMinutes(minutes));
	}

	/**
	 * 时间减少秒数
	 */
	public static Date minusSeconds(Date date, int seconds) {
		return toDate(toLocalDateTime(date).minusSeconds(seconds));
	}

	/**
	 * 时间减少毫秒数
	 */
	public static Date minusMillis(Date date, int millis) {
		return toDate(toLocalDateTime(date).minusNanos(millis * NANO_PER_MILLI));
	}

	/**
	 * 当前时间转String
	 */
	public static String format(String pattern) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 当前时间转String
	 */
	public static String format(DateTimeFormatter formatter) {
		return LocalDateTime.now().format(formatter);
	}

	/**
	 * 当前时间转String 使用
	 */
	public static String format() {
		return format(FORMATTER_DATE_TIME);
	}

	/**
	 * Date转String pattern
	 */
	public static String format(Date date, String pattern) {
		return toLocalDateTime(date).format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * Date转String formatter
	 */
	public static String format(Date date, DateTimeFormatter formatter) {
		return toLocalDateTime(date).format(formatter);
	}

	/**
	 * Date转String
	 */
	public static String formatToTimestamp(Date date) {
		return toLocalDateTime(date).format(FORMATTER_TIMESTAMP);
	}

	/**
	 * Date转String
	 */
	public static String formatToTimestampMs(Date date) {
		return toLocalDateTime(date).format(FORMATTER_TIMESTAMP_MS);
	}

	/**
	 * Date转String
	 */
	public static String formatToDateTime(Date date) {
		return toLocalDateTime(date).format(FORMATTER_DATE_TIME);
	}

	/**
	 * Date转String
	 */
	public static String formatToDateTimeMs(Date date) {
		return toLocalDateTime(date).format(FORMATTER_DATE_TIME_MS);
	}

	/**
	 * Date转String
	 */
	public static String formatToDate(Date date) {
		return toLocalDateTime(date).format(FORMATTER_DATE);
	}

	/**
	 * Date转String
	 */
	public static String formatToTime(Date date) {
		return toLocalDateTime(date).format(FORMATTER_TIME);
	}

	/**
	 * Date转String
	 */
	public static String formatToTimeMs(Date date) {
		return toLocalDateTime(date).format(FORMATTER_TIME_MS);
	}


	/**
	 * String转Date
	 */
	public static Date parse(String string, String pattern) {
		String regex = "";
		Pattern regexPattern = Pattern.compile(regex);
		StringBuilder stringSB = new StringBuilder(string);
		StringBuilder patternSB = new StringBuilder(pattern);
		if (!pattern.contains(PATTERN_YEAR)) {
			patternSB.append(PATTERN_YEAR);
			stringSB.append("1970");
		}
		if (!pattern.contains(PATTERN_MONTH)) {
			patternSB.append(PATTERN_MONTH);
			stringSB.append("01");
		}
		if (!pattern.contains(PATTERN_DAY)) {
			patternSB.append(PATTERN_DAY);
			stringSB.append("01");
		}
		if (!pattern.contains(PATTERN_HOUR)) {
			patternSB.append(PATTERN_HOUR);
			stringSB.append("00");
		}
		if (!pattern.contains(PATTERN_MINUTE)) {
			patternSB.append(PATTERN_MINUTE);
			stringSB.append("00");
		}
		if (!pattern.contains(PATTERN_SECOND)) {
			patternSB.append(PATTERN_SECOND);
			stringSB.append("00");
		}
		return toDate(LocalDateTime.parse(stringSB.toString(), DateTimeFormatter.ofPattern(patternSB.toString())));
	}

	/**
	 * String转Date
	 */
	public static Date parse(String string, DateTimeFormatter formatter) {
		return toDate(LocalDateTime.parse(string, formatter));
	}

	/**
	 * String转Date
	 */
	public static Date parseFromTimestamp(String string) {
		return toDate(LocalDateTime.parse(string, FORMATTER_TIMESTAMP));
	}

	/**
	 * String转Date
	 */
	public static Date parseFromTimestampMs(String string) {
		return toDate(LocalDateTime.parse(string, FORMATTER_TIMESTAMP_MS));
	}

	/**
	 * String转Date
	 */
	public static Date parseFromDateTime(String string) {
		return toDate(LocalDateTime.parse(string, FORMATTER_DATE_TIME));
	}

	/**
	 * String转Date
	 */
	public static Date parseFromDateTimeMs(String string) {
		return toDate(LocalDateTime.parse(string, FORMATTER_DATE_TIME_MS));
	}

	/**
	 * String转Date
	 */
	public static Date parseFromDate(String string) {
		return toDate(LocalDate.parse(string, FORMATTER_DATE));
	}

	/**
	 * String转Date
	 */
	public static Date parseFromTime(String string) {
		return toDate(LocalTime.parse(string, FORMATTER_TIME));
	}

	/**
	 * String转Date
	 */
	public static Date parseFromTimeMs(String string) {
		return toDate(LocalTime.parse(string, FORMATTER_TIME_MS));
	}

}