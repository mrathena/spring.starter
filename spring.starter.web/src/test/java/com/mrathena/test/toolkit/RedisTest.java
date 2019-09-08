package com.mrathena.test.toolkit;

import com.mrathena.common.middleware.Redis;
import com.mrathena.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mrathena on 2019/9/8 21:57
 */
@Slf4j
public class RedisTest extends BaseTest {

	@Autowired
	private Redis redis;

	@Test
	public void test() {
		System.out.println(redis.get("key"));
	}

}
