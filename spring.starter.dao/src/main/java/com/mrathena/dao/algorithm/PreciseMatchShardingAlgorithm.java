package com.mrathena.dao.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 精确匹配分表策略, 传值与表中分片键值完全相同, 与表名后缀完全相同
 *
 * @author mrathena on 2019/6/22 12:27
 */
public class PreciseMatchShardingAlgorithm implements PreciseShardingAlgorithm<String> {

	/**
	 * 精确分片算法, 支持 = IN
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
		return new LinkedList<>(availableTargetNames).getFirst() + shardingValue.getValue();
	}

}
