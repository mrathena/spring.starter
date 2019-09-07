package com.mrathena.dao.toolkit.datasource;

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
	 * 数据源key
	 */
	public final class Key {
		public static final String DATASOURCE = "datasource";
		public static final String DATASOURCE_2 = "datasource2";
		public static final String SHARDING_DATASOURCE = "shardingDatasource";
		public static final String SHARDING_DATASOURCE_2 = "shardingDatasource2";
	}

}
