package com.mrathena.test.dao;

import com.mrathena.common.toolkit.CaptchaKit;
import com.mrathena.dao.entity.customer.CustomerDO;
import com.mrathena.dao.mapper.customer.CustomerMapper;
import com.mrathena.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mrathena on 2019-08-07 21:45
 */
@Slf4j
public class CustomerTest extends BaseTest {

	@Autowired
	private CustomerMapper mapper;

	@Before
	public void before() {
		System.out.println(mapper.insertSelective(new CustomerDO().setCellphone(CaptchaKit.generateCaptcha(11)).setNickname(CaptchaKit.generateCaptcha(16))));
	}

	@Test
	public void test() {
		System.out.println(mapper.selectByPrimaryKey(1L));
	}

}
