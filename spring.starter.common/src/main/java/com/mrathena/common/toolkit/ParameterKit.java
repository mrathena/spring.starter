package com.mrathena.common.toolkit;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mrathena on 2019/4/22 9:38
 */
public final class ParameterKit {

	private ParameterKit() {}

	public static void main(String[] args) {
		Map<String, Object> condition = new HashMap<>(4);
		condition.put("name", "");
		condition.put("categoryNo", 1);
		condition.put("status", "2");
		condition.put("type", null);
		condition.put("provinceCode", "sfsdf  sdfsnihao   ");
		condition.put("cityCode", "");
		System.out.println(removeBlank(trim(condition)));
	}

	/**
	 * 针对value类型为String的,且满足StringUtils.isBlank的做remove操作
	 */
	public static Map<String, Object> removeBlank(Map<String, Object> map) {
		map.entrySet().removeIf(item -> {
			Object object = item.getValue();
			if (object instanceof String) {
				String value = (String) object;
				return StringUtils.isBlank(value);
			}
			return false;
		});
		return map;
	}

	/**
	 * 针对value类型为String的,且满足StringUtils.isEmpty的做remove操作
	 */
	public static Map<String, Object> removeEmpty(Map<String, Object> map) {
		map.entrySet().removeIf(item -> {
			Object object = item.getValue();
			if (object instanceof String) {
				String value = (String) object;
				return StringUtils.isEmpty(value);
			}
			return false;
		});
		return map;
	}

	/**
	 * 针对value类型为String的做trim操作
	 */
	public static Map<String, Object> trim(Map<String, Object> map) {
		map.entrySet().forEach(item -> {
			Object object = item.getValue();
			if (object instanceof String) {
				item.setValue(object.toString().trim());
			}
		});
		return map;
	}

	public static <T> Map<String, Object> beanToMap(T object) throws IllegalAccessException {
		Map<String, Object> map = new HashMap<>(16);
		Class<?> clazz = object.getClass();
		Field[] declaredFieldArray = clazz.getDeclaredFields();
		for (Field field : declaredFieldArray) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(object));
		}
		return map;
	}

	public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) throws IllegalAccessException, InstantiationException {
		T object = clazz.newInstance();
		Field[] declaredFieldArray = object.getClass().getDeclaredFields();
		for (Field field : declaredFieldArray) {
			field.setAccessible(true);
			field.set(object, map.get(field.getName()));
		}
		return object;
	}

}
