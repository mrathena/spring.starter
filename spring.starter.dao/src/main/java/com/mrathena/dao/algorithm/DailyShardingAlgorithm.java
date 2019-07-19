package com.mrathena.dao.algorithm;

import com.mrathena.dao.toolkit.ShardKit;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;

/**
 * 日分片算法
 *
 * @author mrathena on 2019/6/22 12:21
 */
public class DailyShardingAlgorithm implements PreciseShardingAlgorithm<String>, RangeShardingAlgorithm<String> {

	/**
	 * 精确分片算法, 支持 = IN
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
		return ShardKit.doShardingInDate(ShardKit.DateShardingTypeEnum.DAILY, availableTargetNames, shardingValue);
	}

	/**
	 * 范围分片算法, 支持 BETWEEN
	 */
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
		return ShardKit.doShardingInDate(ShardKit.DateShardingTypeEnum.DAILY, availableTargetNames, shardingValue);
	}

}
