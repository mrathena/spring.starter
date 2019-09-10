package com.mrathena.dao.toolkit.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 如果一个方法加了注解@Transactional, 那么该方法内数据源的切换将会失效,
 * 第一条sql用到的那个数据源会被事务代理对象缓存起来, 后续sql直接使用, 而不会重新创建链接
 * 表现出来就是, DynamicDataSourceHolder 的 set 和 clear 方法在不停调用, 但是中间没有 get 方法的调用
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
