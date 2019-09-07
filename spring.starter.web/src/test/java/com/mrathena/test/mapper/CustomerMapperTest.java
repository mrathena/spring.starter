package com.mrathena.test.mapper;

import com.mrathena.dao.mapper.customer.CustomerMapper;
import com.mrathena.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mrathena on 2019-09-07 23:14
 */
public class CustomerMapperTest extends BaseTest {

	@Autowired
	private CustomerMapper customerMapper;

	@Test
	public void test() {
		System.out.println(customerMapper.selectByPrimaryKey(1L));
	}

}
