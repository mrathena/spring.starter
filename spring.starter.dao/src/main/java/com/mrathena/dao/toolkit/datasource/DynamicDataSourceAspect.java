package com.mrathena.dao.toolkit.datasource;

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
		if (method.isAnnotationPresent(DataSource.class)) {
			DynamicDataSourceHolder.set(method.getAnnotation(DataSource.class).value());
			return;
		}
		Class<?> clazz = point.getTarget().getClass().getInterfaces()[0];
		if (clazz.isAnnotationPresent(DataSource.class)) {
			DynamicDataSourceHolder.set(clazz.getAnnotation(DataSource.class).value());
		}
	}

	/**
	 * 后置通知
	 */
	public void after() {
		DynamicDataSourceHolder.clear();
	}

}
