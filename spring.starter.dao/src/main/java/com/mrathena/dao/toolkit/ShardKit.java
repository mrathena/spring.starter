package com.mrathena.dao.toolkit;

import com.google.common.collect.Range;
import com.mrathena.common.exception.ExceptionCode;
import com.mrathena.common.exception.ServiceException;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 分库分表常用工具
 *
 * @author mrathena on 2019/6/22 12:29
 */
@Slf4j
public final class ShardKit {

	public static void main(String[] args) {
		LocalDate min = LocalDate.of(2018, 1, 1);
		LocalDate max = LocalDate.of(2020, 2, 28);
		System.out.println(generateRangeShardingKeySet(DateShardingTypeEnum.DAILY, min, max));
		System.out.println(generateRangeShardingKeySet(DateShardingTypeEnum.MONTHLY, "201906", "201806"));
	}

	private ShardKit() {}

	private static final DateTimeFormatter FORMATTER_DAILY = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final DateTimeFormatter FORMATTER_MONTHLY = DateTimeFormatter.ofPattern("yyyyMM");
	private static final DateTimeFormatter FORMATTER_YEARLY = DateTimeFormatter.ofPattern("yyyy");

	/**
	 * 按日期分片模式枚举
	 */
	public enum DateShardingTypeEnum {
		/**
		 * 年,月,日
		 */
		YEARLY, MONTHLY, DAILY
	}

	/**
	 * 按日期精确分片算法, 支持 = IN
	 */
	public static String doShardingInDate(DateShardingTypeEnum type, Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
		return new LinkedList<>(availableTargetNames).getFirst() + getSuffixByShardingType(type, shardingValue.getValue());
	}

	/**
	 * 根据日期分片模式获取表名后缀
	 */
	private static String getSuffixByShardingType(DateShardingTypeEnum type, String shardingValue) {
		switch (type) {
			case YEARLY:
				return shardingValue.substring(0, 4);
			case MONTHLY:
				return shardingValue.substring(0, 6);
			case DAILY:
				return shardingValue;
			default:
				throw new ServiceException(ExceptionCode.DAO_ERROR.name(), "未知日期分片模式");
		}
	}

	/**
	 * 按日期范围分片算法, 支持 BETWEEN
	 */
	public static Collection<String> doShardingInDate(DateShardingTypeEnum type, Collection<String> availableTargetNames, RangeShardingValue<String>
			shardingValue) {
		Range<String> range = shardingValue.getValueRange();
		Set<String> shardingKeySet = generateRangeShardingKeySet(type, range.lowerEndpoint(), range.upperEndpoint());
		Set<String> tableNameSet = new LinkedHashSet<>(availableTargetNames.size() * shardingKeySet.size());
		for (String prefix : availableTargetNames) {
			for (String suffix : shardingKeySet) {
				tableNameSet.add(prefix + suffix);
			}
		}
		return tableNameSet;
	}

	/**
	 * 根据传入的最小和最大分片键生成连续的分片建
	 * 举例: 传入MONTHLY:20180101-20180630, 生成[201801,201802,201803,201804,201805,201806]
	 * 举例: 传入YEARLY:20180101-20190630, 生成[2018,2019]
	 */
	public static Set<String> generateRangeShardingKeySet(DateShardingTypeEnum type, String minLocalDateStr, String maxLocalDateStr) {
		LocalDate minLocalDate = LocalDate.parse(getCompleteDateStringByShardingType(type, minLocalDateStr), FORMATTER_DAILY);
		LocalDate maxLocalDate = LocalDate.parse(getCompleteDateStringByShardingType(type, maxLocalDateStr), FORMATTER_DAILY);
		return generateRangeShardingKeySet(type, minLocalDate, maxLocalDate);
	}

	/**
	 * 根据日期分片模式获取对应日期的完整日期格式字符串(补充到yyyyMMdd格式)
	 */
	private static String getCompleteDateStringByShardingType(DateShardingTypeEnum type, String dateString) {
		switch (type) {
			case YEARLY:
				return dateString.substring(0, 4) + "0101";
			case MONTHLY:
				return dateString.substring(0, 6) + "01";
			case DAILY:
				return dateString;
			default:
				throw new ServiceException(ExceptionCode.DAO_ERROR.name(), "未知日期分片模式");
		}
	}

	/**
	 * 根据传入的最小和最大分片键生成连续的分片建
	 * 举例: 传入MONTHLY:20180101-20180630, 生成[201801,201802,201803,201804,201805,201806]
	 * 举例: 传入YEARLY:20180101-20190630, 生成[2018,2019]
	 */
	private static Set<String> generateRangeShardingKeySet(DateShardingTypeEnum type, LocalDate minLocalDate, LocalDate maxLocalDate) {
		Set<String> keySet = new TreeSet<>();
		while (!minLocalDate.isAfter(maxLocalDate)) {
			keySet.add(minLocalDate.format(getFormatterByShardingType(type)));
			minLocalDate = getNextLocalDateByShardingType(type, minLocalDate);
		}
		return keySet;
	}

	/**
	 * 根据日期分片模式获取对应日期时间格式器
	 */
	private static DateTimeFormatter getFormatterByShardingType(DateShardingTypeEnum type) {
		switch (type) {
			case YEARLY:
				return FORMATTER_YEARLY;
			case MONTHLY:
				return FORMATTER_MONTHLY;
			case DAILY:
				return FORMATTER_DAILY;
			default:
				throw new ServiceException(ExceptionCode.DAO_ERROR.name(), "未知日期分片模式");
		}
	}

	/**
	 * 根据日期分片模式获取下一个日期
	 */
	private static LocalDate getNextLocalDateByShardingType(DateShardingTypeEnum type, LocalDate localDate) {
		switch (type) {
			case YEARLY:
				return localDate.plusYears(1);
			case MONTHLY:
				return localDate.plusMonths(1);
			case DAILY:
				return localDate.plusDays(1);
			default:
				throw new ServiceException(ExceptionCode.DAO_ERROR.name(), "未知日期分片模式");
		}
	}

}
