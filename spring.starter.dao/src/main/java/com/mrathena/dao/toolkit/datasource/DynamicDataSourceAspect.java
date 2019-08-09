package com.mrathena.dao.toolkit.datasource;

import com.mrathena.dao.toolkit.datasource.annotation.ShardingDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author mrathena on 2019/8/8 23:47
 */
public class DynamicDataSourceAspect {

	/**
	 * 前置通知
	 */
	public void before(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		if (method.isAnnotationPresent(ShardingDataSource.class)) {
			DynamicDataSourceHolder.set(DynamicDataSource.Key.SHARDING);
			return;
		}
		Class<?> clazz = point.getTarget().getClass();
		if (clazz.isAnnotationPresent(ShardingDataSource.class)) {
			DynamicDataSourceHolder.set(DynamicDataSource.Key.SHARDING);
		}
	}

	/**
	 * 后置通知
	 */
	public void after(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		if (method.isAnnotationPresent(ShardingDataSource.class)) {
			DynamicDataSourceHolder.clear();
			return;
		}
		Class<?> clazz = point.getTarget().getClass();
		if (clazz.isAnnotationPresent(ShardingDataSource.class)) {
			DynamicDataSourceHolder.clear();
		}
	}

}
