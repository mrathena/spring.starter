package com.mrathena.common.constant;

/**
 * @author mrathena on 2019/5/25 19:42
 */
public final class RedisConstant {

	private RedisConstant() {}

	/**
	 * 用毫秒来表示一秒
	 */
	public static final long SECOND_IN_MILLI = 1000L;
	/**
	 * 用毫秒来表示一分钟
	 */
	public static final long MINUTE_IN_MILLI = SECOND_IN_MILLI * 60;
	/**
	 * 用毫秒来表示一小时
	 */
	public static final long HOUR_IN_MILLI = MINUTE_IN_MILLI * 60;
	/**
	 * 用毫秒来表示一天
	 */
	public static final long DAY_IN_MILLI = HOUR_IN_MILLI * 24;
	/**
	 * 用毫秒来表示一月
	 */
	public static final long MONTH_IN_MILLI = DAY_IN_MILLI * 30;
	/**
	 * 用毫秒来表示一年360天
	 */
	public static final long YEAR_IN_MILLI = MONTH_IN_MILLI * 12;

	/**
	 * 用秒来表示一分钟
	 */
	public static final long MINUTE_IN_SECOND = 60L;
	/**
	 * 用秒来表示一小时
	 */
	public static final long HOUR_IN_SECOND = MINUTE_IN_SECOND * 60;
	/**
	 * 用秒来表示一天
	 */
	public static final long DAY_IN_SECOND = HOUR_IN_SECOND * 24;
	/**
	 * 用秒来表示一月
	 */
	public static final long MONTH_IN_SECOND = DAY_IN_SECOND * 30;
	/**
	 * 用秒来表示一年360天
	 */
	public static final long YEAR_IN_SECOND = MONTH_IN_SECOND * 12;

	/**
	 * 分布式锁默认超时时间(用毫秒来表示的一小时)
	 */
	public static final long DISTRIBUTED_LOCK_TIMEOUT = HOUR_IN_MILLI;

}
