package com.mrathena.dao.toolkit.datasource;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
	@Getter
	@AllArgsConstructor
	public enum Key {
		/**
		 * 默认数据源
		 */
		DEFAULT("datasource"),
		/**
		 * 分表数据源
		 */
		SHARDING("shardingDatasource");

		private String code;
	}

}
