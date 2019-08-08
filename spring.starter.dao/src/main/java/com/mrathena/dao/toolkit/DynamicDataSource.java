package com.mrathena.dao.toolkit;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author mrathena on 2019/8/8 22:16
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.get();
	}

	/**
	 * 可用的动态数据源Key
	 */
	public static class Key {
		public static final String DEFAULT = "datasource";
		public static final String SHARDING = "shardingDatasource";
	}

}
