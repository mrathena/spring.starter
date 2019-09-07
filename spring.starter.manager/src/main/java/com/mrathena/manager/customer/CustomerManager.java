package com.mrathena.manager.customer;

import com.mrathena.dao.entity.customer.CustomerDO;
import com.mrathena.dao.mapper.customer.CustomerMapper;
import com.mrathena.dao.toolkit.datasource.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mrathena on 2019/8/9 1:18
 */
@Component
@DataSource
public class CustomerManager {

	@Autowired
	private CustomerMapper mapper;

	public CustomerDO queryById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	public List<CustomerDO> queryByCellphone(String cellphone) {
		return mapper.selectByCellphone(cellphone);
	}

}
