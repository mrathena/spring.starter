package com.mrathena.dao.algorithm;

import com.google.common.collect.Range;
import com.mrathena.dao.toolkit.ShardKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 年分片算法
 *
 * @author mrathena on 2019/6/22 12:17
 */
@Slf4j
public class YearlyTableShardingAlgorithm implements PreciseShardingAlgorithm<String>, RangeShardingAlgorithm<String> {

	/**
	 * 精确分片算法, 支持 = IN
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
		String suffix = shardingValue.getValue().substring(0, 4);
		for (String name : availableTargetNames) {
			if (name.endsWith(suffix)) {
				return name;
			}
		}
		return null;
	}

	/**
	 * 范围分片算法, 支持 BETWEEN
	 */
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
		Range<String> range = shardingValue.getValueRange();
		Set<String> shardingKeySet =
				ShardKit.generateRangeShardingKeySet(ShardKit.DateShardingTypeEnum.YEARLY, range.lowerEndpoint(), range.upperEndpoint());
		Set<String> tableNameSet = new HashSet<>(availableTargetNames.size());
		for (String name : availableTargetNames) {
			for (String suffix : shardingKeySet) {
				if (name.endsWith(suffix)) {
					tableNameSet.add(name);
				}
			}
		}
		return tableNameSet;
	}

}
