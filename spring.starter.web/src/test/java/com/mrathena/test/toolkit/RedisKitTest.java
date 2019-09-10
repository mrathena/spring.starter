package com.mrathena.test.toolkit;

import com.mrathena.common.middleware.RedisKit;
import com.mrathena.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mrathena on 2019/9/8 21:57
 */
@Slf4j
public class RedisKitTest extends BaseTest {

	@Autowired
	private RedisKit redisKit;

	@Test
	public void test() {
		System.out.println(redisKit.get("key"));
	}

}
