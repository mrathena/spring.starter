package com.mrathena.test.dao;

import com.mrathena.manager.customer.CustomerManager;
import com.mrathena.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mrathena on 2019-08-07 21:45
 */
@Slf4j
public class CustomerTest extends BaseTest {

	@Autowired
	private CustomerManager manager;

	@Test
	public void test() {
		System.out.println(manager.queryById(1L));
		System.out.println("-------------");
		System.out.println(manager.queryByCellphone("18234089811"));
	}

}
