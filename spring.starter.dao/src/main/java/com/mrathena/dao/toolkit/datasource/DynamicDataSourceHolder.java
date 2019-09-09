package com.mrathena.dao.toolkit.datasource;

import lombok.extern.slf4j.Slf4j;

/**
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
