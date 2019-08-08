package com.mrathena.dao.toolkit;

/**
 * 针对线程有效,而且要放在事务之前(不然事务不生效),放在service或者biz的开头就可以了
 * DynamicDataSourceHolder.set(DynamicDataSource.Key.SHARDING);
 *
 * @author mrathena on 2019/8/8 22:17
 */
public class DynamicDataSourceHolder {

	private static final ThreadLocal<String> DATASOURCE = new ThreadLocal<>();

	public static String get() {
		return DATASOURCE.get();
	}

	public static void set(String datasource) {
		DATASOURCE.set(datasource);
	}

	public static void clear() {
		DATASOURCE.remove();
	}

}
