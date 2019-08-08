package com.mrathena.dao.toolkit;

/**
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
