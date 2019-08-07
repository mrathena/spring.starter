package com.mrathena.dao.mapper.customer;

import com.mrathena.dao.entity.customer.CustomerDO;

/**
 * customer
 *
 * @author mrathena on 2019/08/07 21:28
 */
public interface CustomerMapper {

	/**
	 * deleteByPrimaryKey
	 *
	 * @param id .
	 * @return .
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * insert
	 *
	 * @param record .
	 * @return .
	 */
	int insert(CustomerDO record);

	/**
	 * insertSelective
	 *
	 * @param record .
	 * @return .
	 */
	int insertSelective(CustomerDO record);

	/**
	 * selectByPrimaryKey
	 *
	 * @param id .
	 * @return .
	 */
	CustomerDO selectByPrimaryKey(Long id);

	/**
	 * updateByPrimaryKeySelective
	 *
	 * @param record .
	 * @return .
	 */
	int updateByPrimaryKeySelective(CustomerDO record);

	/**
	 * updateByPrimaryKey
	 *
	 * @param record .
	 * @return .
	 */
	int updateByPrimaryKey(CustomerDO record);
}
