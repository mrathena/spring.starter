package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ServiceException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author mrathena on 2018-09-27 14:59:04
 */
public final class DateTimeKit {

	public static void main(String[] args) {
		LocalDateTime start = LocalDateTime.of(2019, 9, 1, 0, 0, 0, 0);
		LocalDateTime stop = LocalDateTime.of(2019, 9, 1, 0, 0, 0, 1);
		LocalDateTime anotherStart = LocalDateTime.of(2019, 9, 1, 0, 0, 0, 2);
		LocalDateTime anotherStop = LocalDateTime.of(2019, 9, 1, 0, 0, 0, 2);
		System.out.println(DateTimeKit.isLocalDateTimeIntervalOverlap(start, stop, anotherStart, anotherStop));

		LocalDate start2 = LocalDate.of(2019, 9, 1);
		LocalDate stop2 = LocalDate.of(2019, 9, 2);
		LocalDate anotherStart2 = LocalDate.of(2019, 9, 3);
		LocalDate anotherStop2 = LocalDate.of(2019, 9, 4);
		System.out.println(DateTimeKit.isLocalDateIntervalOverlap(start2, stop2, anotherStart2, anotherStop2));
	}

	private DateTimeKit() {}

	public static final String PATTERN_YEAR = "yyyy";
	public static final String PATTERN_MONTH = "MM";
	public static final String PATTERN_DAY = "dd";
	public static final String PATTERN_HOUR = "HH";
	public static final String PATTERN_MINUTE = "mm";
	public static final String PATTERN_SECOND = "ss";
	public static final String PATTERN_MILLI = "SSS";

	public static final String PATTERN_YEAR_MONTH = "yyyy-MM";
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_TIME = "HH:mm:ss";
	public static final String PATTERN_TIME_MILLI = "HH:mm:ss.SSS";
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE_TIME_MILLI = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String PATTERN_COMPACT_YEAR_MONTH = "yyyyMM";
	public static final String PATTERN_COMPACT_DATE = "yyyyMMdd";
	public static final String PATTERN_COMPACT_TIME = "HHmmss";
	public static final String PATTERN_COMPACT_TIME_MILLI = "HHmmssSSS";
	public static final String PATTERN_COMPACT_DATE_TIME = "yyyyMMddHHmmss";
	public static final String PATTERN_COMPACT_DATE_TIME_MILLI = "yyyyMMddHHmmssSSS";

	public static final DateTimeFormatter FORMATTER_YEAR = DateTimeFormatter.ofPattern(PATTERN_YEAR);
	public static final DateTimeFormatter FORMATTER_MONTH = DateTimeFormatter.ofPattern(PATTERN_MONTH);
	public static final DateTimeFormatter FORMATTER_DAY = DateTimeFormatter.ofPattern(PATTERN_DAY);
	public static final DateTimeFormatter FORMATTER_HOUR = DateTimeFormatter.ofPattern(PATTERN_HOUR);
	public static final DateTimeFormatter FORMATTER_MINUTE = DateTimeFormatter.ofPattern(PATTERN_MINUTE);
	public static final DateTimeFormatter FORMATTER_SECOND = DateTimeFormatter.ofPattern(PATTERN_SECOND);
	public static final DateTimeFormatter FORMATTER_MILLI = DateTimeFormatter.ofPattern(PATTERN_MILLI);

	public static final DateTimeFormatter FORMATTER_YEAR_MONTH = DateTimeFormatter.ofPattern(PATTERN_YEAR_MONTH);
	public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern(PATTERN_DATE);
	public static final DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern(PATTERN_TIME);
	public static final DateTimeFormatter FORMATTER_TIME_MILLI = DateTimeFormatter.ofPattern(PATTERN_TIME_MILLI);
	public static final DateTimeFormatter FORMATTER_DATE_TIME = DateTimeFormatter.ofPattern(PATTERN_DATE_TIME);
	public static final DateTimeFormatter FORMATTER_DATE_TIME_MILLI = DateTimeFormatter.ofPattern(PATTERN_DATE_TIME_MILLI);

	public static final DateTimeFormatter FORMATTER_COMPACT_YEAR_MONTH = DateTimeFormatter.ofPattern(PATTERN_COMPACT_YEAR_MONTH);
	public static final DateTimeFormatter FORMATTER_COMPACT_DATE = DateTimeFormatter.ofPattern(PATTERN_COMPACT_DATE);
	public static final DateTimeFormatter FORMATTER_COMPACT_TIME = DateTimeFormatter.ofPattern(PATTERN_COMPACT_TIME);
	public static final DateTimeFormatter FORMATTER_COMPACT_TIME_MILLI = DateTimeFormatter.ofPattern(PATTERN_COMPACT_TIME_MILLI);
	public static final DateTimeFormatter FORMATTER_COMPACT_DATE_TIME = DateTimeFormatter.ofPattern(PATTERN_COMPACT_DATE_TIME);
	public static final DateTimeFormatter FORMATTER_COMPACT_DATE_TIME_MILLI = DateTimeFormatter.ofPattern(PATTERN_COMPACT_DATE_TIME_MILLI);

	// 纪元日期时间(已去除时区影响)
	public static final Date EPOCH_DATE = new Date(-28_800_000);
	public static final LocalDateTime EPOCH_LOCAL_DATE_TIME = LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0);
	public static final LocalDate EPOCH_LOCAL_DATE = LocalDate.of(1970, 1, 1);
	public static final LocalTime START_LOCAL_TIME = LocalTime.of(0, 0, 0, 0);

	// 一毫秒=1000000纳秒
	private static final int MILLI_IN_NANO = 1_000_000;

	// 日期时间区间重叠判断

	public static boolean isLocalDateTimeIntervalOverlap(LocalDateTime start, LocalDateTime stop, LocalDateTime anotherStart, LocalDateTime anotherStop) {
		if (start == null || stop == null || anotherStart == null || anotherStop == null) {
			throw new ServiceException("比较时间不合法", "四个LocalDateTime必须都不为null");
		}
		if (start.isAfter(stop) || anotherStart.isAfter(anotherStop)) {
			throw new ServiceException("结束时间必须晚于开始时间", "两组结束时间必须晚于同组开始时间");
		}
		LocalDateTime minDateTime = start.isBefore(anotherStart) ? start : anotherStart;
		LocalDateTime maxDateTime = stop.isBefore(anotherStop) ? anotherStop : stop;
		long nanos = getEpochNano(maxDateTime) - getEpochNano(minDateTime);
		long nanos1 = getEpochNano(stop) - getEpochNano(start);
		long nanos2 = getEpochNano(anotherStop) - getEpochNano(anotherStart);
		return nanos <= (nanos1 + nanos2);
	}

	public static long getEpochNano(LocalDateTime localDateTime) {
		Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		return instant.getEpochSecond() * 1_000_000_000 + instant.getNano();
	}

	public static boolean isLocalDateIntervalOverlap(LocalDate start, LocalDate stop, LocalDate anotherStart, LocalDate anotherStop) {
		return isLocalDateTimeIntervalOverlap(start.atStartOfDay(), stop.atStartOfDay(), anotherStart.atStartOfDay(), anotherStop.atStartOfDay());
	}

	public static boolean isDataIntervalOverlap(Date start, Date stop, Date anotherStart, Date anotherStop) {
		if (start == null || stop == null || anotherStart == null || anotherStop == null) {
			throw new ServiceException("比较时间不合法", "四个LocalDateTime必须都不为null");
		}
		if (start.after(stop) || anotherStart.after(anotherStop)) {
			throw new ServiceException("结束时间必须晚于开始时间", "两组结束时间必须晚于同组开始时间");
		}
		Date minDate = start.before(anotherStart) ? start : anotherStart;
		Date maxDate = stop.before(anotherStop) ? anotherStop : stop;
		long millis = maxDate.getTime() - minDate.getTime();
		long millis1 = stop.getTime() - start.getTime();
		long millis2 = anotherStop.getTime() - anotherStart.getTime();
		return millis <= (millis1 + millis2);
	}

	// Date 和 LocalDateTime,LocalDate,LocalTime 的互相转换
	// Date -> LocalDateTime: Date -> Instant -> ZonedDateTime -> LocalDateTime
	// LocalDateTime -> Date: LocalDateTime -> ZonedDateTime -> Instant -> Date

	public static LocalDateTime toLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalTime toLocalTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
	}

	public static Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date toDate(LocalDate localDate) {
		return toDate(localDate.atTime(START_LOCAL_TIME));
	}

	public static Date toDate(LocalTime localTime) {
		return toDate(localTime.atDate(EPOCH_LOCAL_DATE));
	}

	// 生成指定的 Date

	public static Date toBeginDate(Date date) {
		return toDate(toLocalDate(date));
	}

	public static Date toEndDate(Date date) {
		return toDate(toLocalDateTime(date).with(LocalTime.MAX));
	}

	public static Date newDateTime(int year, int month, int day, int hour, int minute, int second) {
		return toDate(LocalDateTime.of(year, month, day, hour, minute, second));
	}

	public static Date newDateTime(int year, int month, int day, int hour, int minute, int second, int milli) {
		return toDate(LocalDateTime.of(year, month, day, hour, minute, second, milli * MILLI_IN_NANO));
	}

	public static Date newDate(int year, int month, int day) {
		return toDate(LocalDate.of(year, month, day));
	}

	public static Date newTime(int hour, int minute, int second) {
		return toDate(LocalTime.of(hour, minute, second));
	}

	public static Date newTime(int hour, int minute, int second, int milli) {
		return toDate(LocalTime.of(hour, minute, second, milli * MILLI_IN_NANO));
	}

	// Date 的加减操作

	public static Date plusYear(Date date, int year) {
		return toDate(toLocalDateTime(date).plusYears(year));
	}

	public static Date plusMonth(Date date, int month) {
		return toDate(toLocalDateTime(date).plusMonths(month));
	}

	public static Date plusWeek(Date date, int week) {
		return toDate(toLocalDateTime(date).plusWeeks(week));
	}

	public static Date plusDay(Date date, int day) {
		return toDate(toLocalDateTime(date).plusDays(day));
	}

	public static Date plusHour(Date date, int hour) {
		return toDate(toLocalDateTime(date).plusHours(hour));
	}

	public static Date plusMinute(Date date, int minute) {
		return toDate(toLocalDateTime(date).plusMinutes(minute));
	}

	public static Date plusSecond(Date date, int second) {
		return toDate(toLocalDateTime(date).plusSeconds(second));
	}

	public static Date plusMilli(Date date, int milli) {
		return toDate(toLocalDateTime(date).plusNanos(milli * MILLI_IN_NANO));
	}

	public static Date minusYear(Date date, int year) {
		return toDate(toLocalDateTime(date).minusYears(year));
	}

	public static Date minusMonth(Date date, int month) {
		return toDate(toLocalDateTime(date).minusMonths(month));
	}

	public static Date minusWeek(Date date, int week) {
		return toDate(toLocalDateTime(date).minusWeeks(week));
	}

	public static Date minusDay(Date date, int day) {
		return toDate(toLocalDateTime(date).minusDays(day));
	}

	public static Date minusHour(Date date, int hour) {
		return toDate(toLocalDateTime(date).minusHours(hour));
	}

	public static Date minusMinute(Date date, int minute) {
		return toDate(toLocalDateTime(date).minusMinutes(minute));
	}

	public static Date minusSecond(Date date, int second) {
		return toDate(toLocalDateTime(date).minusSeconds(second));
	}

	public static Date minusMilli(Date date, int milli) {
		return toDate(toLocalDateTime(date).minusNanos(milli * MILLI_IN_NANO));
	}

	// 当前时间格式化为字符串

	public static String now(DateTimeFormatter formatter) {
		return LocalDateTime.now().format(formatter);
	}

	public static String now(String pattern) {
		return now(DateTimeFormatter.ofPattern(pattern));
	}

	public static String now() {
		return now(FORMATTER_DATE_TIME);
	}

	public static String nowToYear() {
		return now(FORMATTER_YEAR);
	}

	public static String nowToYearMonth() {
		return now(FORMATTER_YEAR_MONTH);
	}

	public static String nowToDate() {
		return now(FORMATTER_DATE);
	}

	public static String nowToTime() {
		return now(FORMATTER_TIME);
	}

	public static String nowToTimeMilli() {
		return now(FORMATTER_TIME_MILLI);
	}

	public static String nowToDateTime() {
		return now(FORMATTER_DATE_TIME);
	}

	public static String nowToDateTimeMilli() {
		return now(FORMATTER_DATE_TIME_MILLI);
	}

	public static String nowToCompactYearMonth() {
		return now(FORMATTER_COMPACT_YEAR_MONTH);
	}

	public static String nowToCompactDate() {
		return now(FORMATTER_COMPACT_DATE);
	}

	public static String nowToCompactTime() {
		return now(FORMATTER_COMPACT_TIME);
	}

	public static String nowToCompactTimeMilli() {
		return now(FORMATTER_COMPACT_TIME_MILLI);
	}

	public static String nowToCompactDateTime() {
		return now(FORMATTER_COMPACT_DATE_TIME);
	}

	public static String nowToCompactDateTimeMilli() {
		return now(FORMATTER_COMPACT_DATE_TIME_MILLI);
	}

	// 指定时间格式化为字符串

	public static String format(Date date, DateTimeFormatter formatter) {
		return toLocalDateTime(date).format(formatter);
	}

	public static String format(Date date, String pattern) {
		return format(date, DateTimeFormatter.ofPattern(pattern));
	}

	public static String format(Date date) {
		return format(date, FORMATTER_DATE_TIME);
	}

	public static String formatToYear(Date date) {
		return format(date, FORMATTER_YEAR);
	}

	public static String formatToYearMonth(Date date) {
		return format(date, FORMATTER_YEAR_MONTH);
	}

	public static String formatToDate(Date date) {
		return format(date, FORMATTER_DATE);
	}

	public static String formatToTime(Date date) {
		return format(date, FORMATTER_TIME);
	}

	public static String formatToTimeMilli(Date date) {
		return format(date, FORMATTER_TIME_MILLI);
	}

	public static String formatToDateTime(Date date) {
		return format(date, FORMATTER_DATE_TIME);
	}

	public static String formatToDateTimeMilli(Date date) {
		return format(date, FORMATTER_DATE_TIME_MILLI);
	}

	public static String formatToCompactYearMonth(Date date) {
		return format(date, FORMATTER_COMPACT_YEAR_MONTH);
	}

	public static String formatToCompactDate(Date date) {
		return format(date, FORMATTER_COMPACT_DATE);
	}

	public static String formatToCompactTime(Date date) {
		return format(date, FORMATTER_COMPACT_TIME);
	}

	public static String formatToCompactTimeMilli(Date date) {
		return format(date, FORMATTER_COMPACT_TIME_MILLI);
	}

	public static String formatToCompactDateTime(Date date) {
		return format(date, FORMATTER_COMPACT_DATE_TIME);
	}

	public static String formatToCompactDateTimeMilli(Date date) {
		return format(date, FORMATTER_COMPACT_DATE_TIME_MILLI);
	}

	// 字符串转换为 Date

	public static Date parse(String string, DateTimeFormatter formatter) {
		return toDate(LocalDateTime.parse(string, formatter));
	}

	public static Date parse(String string, String pattern) {
		return parse(string, DateTimeFormatter.ofPattern(pattern));
	}

	public static Date parseFromDate(String string) {
		return toDate(LocalDate.parse(string, FORMATTER_DATE));
	}

	public static Date parseFromTime(String string) {
		return toDate(LocalTime.parse(string, FORMATTER_TIME));
	}

	public static Date parseFromTimeMilli(String string) {
		return toDate(LocalTime.parse(string, FORMATTER_TIME_MILLI));
	}

	public static Date parseFromDateTime(String string) {
		return toDate(LocalDateTime.parse(string, FORMATTER_DATE_TIME));
	}

	public static Date parseFromDateTimeMilli(String string) {
		return toDate(LocalDateTime.parse(string, FORMATTER_DATE_TIME_MILLI));
	}

	public static Date parseFromCompactDate(String string) {
		return toDate(LocalDate.parse(string, FORMATTER_COMPACT_DATE));
	}

	public static Date parseFromCompactTime(String string) {
		return toDate(LocalTime.parse(string, FORMATTER_COMPACT_TIME));
	}

	public static Date parseFromCompactTimeMilli(String string) {
		return toDate(LocalTime.parse(string, FORMATTER_COMPACT_TIME_MILLI));
	}

	public static Date parseFromCompactDateTime(String string) {
		return toDate(LocalDateTime.parse(string, FORMATTER_COMPACT_DATE_TIME));
	}

	public static Date parseFromCompactDateTimeMilli(String string) {
		return toDate(LocalDateTime.parse(string, FORMATTER_COMPACT_DATE_TIME_MILLI));
	}

}