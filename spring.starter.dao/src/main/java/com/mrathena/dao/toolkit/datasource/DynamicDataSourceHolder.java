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

	private static final ThreadLocal<String> DATASOURCE = new ThreadLocal<>();

	public static String get() {
		return DATASOURCE.get();
	}

	public static void set(DynamicDataSource.Key datasource) {
		DATASOURCE.set(datasource.getCode());
	}

	public static void clear() {
		DATASOURCE.remove();
	}

}
