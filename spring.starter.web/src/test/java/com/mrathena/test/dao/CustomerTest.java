package com.mrathena.test.dao;

import com.mrathena.dao.mapper.customer.CustomerMapper;
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
	private CustomerMapper mapper;

	@Test
	public void test() {
		System.out.println(mapper.selectByPrimaryKey(1L));
	}

}
