package com.mrathena.common.middleware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author mrathena on 2019/9/8 21:50
 */
@Slf4j
@Component
public class RedisKit {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public Object get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

}
