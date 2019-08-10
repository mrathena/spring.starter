package com.mrathena.test.manager;

import com.mrathena.manager.customer.CustomerManager;
import com.mrathena.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mrathena on 2019-08-07 21:45
 */
@Slf4j
public class CustomerManagerTest extends BaseTest {

	@Autowired
	private CustomerManager manager;

	@Test
	public void test() {
		log.info("{}", manager.queryById(1L));
		System.out.println("-------------");
		log.info("{}", manager.queryByCellphone("18234089811"));
	}

}
