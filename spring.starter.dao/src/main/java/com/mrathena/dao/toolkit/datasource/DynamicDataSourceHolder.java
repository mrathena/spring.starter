package com.mrathena.dao.toolkit.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * 针对线程有效,而且要放在事务之前(不然事务不生效)
 * 如果有事务,放在service或者biz的开头就可以了,如果没有事务,放在其他地方也可以
 * DynamicDataSourceHolder.set(DynamicDataSource.Key.SHARDING);
 *
 * @author mrathena on 2019/8/8 22:17
 */
@Slf4j
public class DynamicDataSourceHolder {

	/**
	 * 存的是动态数据源Map的key
	 */
	private static final ThreadLocal<String> KEY = new ThreadLocal<>();

	public static String get() {
		return KEY.get();
	}

	/**
	 * com.mrathena.dao.toolkit.datasource.DynamicDataSource.Key
	 */
	public static void set(String key) {
		KEY.set(key);
	}

	public static void clear() {
		KEY.remove();
	}

}
