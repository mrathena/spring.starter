package com.mrathena.dao.toolkit.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 如果一个方法中会切多个数据源,则该方法不能加事务注解@Transactional,
 * 不然数据源会保持在第一次切的那个数据源不在变化(不会调用determineCurrentLookupKey()),
 * 表现出来就是, 不停的set和clear, 但是没有get
 *
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
