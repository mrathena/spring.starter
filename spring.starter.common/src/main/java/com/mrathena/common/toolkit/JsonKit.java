package com.mrathena.common.toolkit;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @author mrathena
 */
public final class JsonKit {

	public static void main(String[] args) {
		Map<String, Object> map = new LinkedHashMap<>(10);
		Map<String, Object> id = new HashMap<>(2);
		id.put("idCardName", "张建峰");
		id.put("idCardNo", "12345");
		List<Integer> scores = new ArrayList<>(3);
		scores.add(91);
		scores.add(87);
		scores.add(12);
		map.put("username", "邂逅");
		map.put("id", id);
		map.put("scores", scores);
		System.out.println(map);
		System.out.println(JSON.toJSONString(map));
		System.out.println(jsonToObject(JSON.toJSONString(map)));
	}

	private JsonKit() {}

	private static final String ARRAY_PREFIX = "[";
	private static final String OBJECT_PREFIX = "{";

	/**
	 * JSON字符串转Object
	 * 递归转换
	 */
	@SuppressWarnings("unchecked")
	public static Object jsonToObject(String json) {
		if (json.startsWith(ARRAY_PREFIX)) {
			@SuppressWarnings("rawtypes")
			List list = JSON.parseArray(json);
			List<Object> result = new LinkedList<>();
			for (Object item : list) {
				if (item != null && item.toString().startsWith("{")) {
					result.add(jsonToObject(item.toString()));
				} else {
					result.add(item);
				}
			}
			return result;
		} else if (json.startsWith(OBJECT_PREFIX)) {
			Map<String, Object> result = JSON.parseObject(json, LinkedHashMap.class);
			for (String key : result.keySet()) {
				Object value = result.get(key);
				if (value != null && value.toString().startsWith("{")) {
					result.put(key, jsonToObject(value.toString()));
				}
			}
			return result;
		}
		return null;
	}

}