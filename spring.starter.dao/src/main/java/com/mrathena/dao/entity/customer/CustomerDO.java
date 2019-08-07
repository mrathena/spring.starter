package com.mrathena.dao.entity.customer;

import com.mrathena.dao.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * customer
 *
 * @author mrathena on 2019/08/07 21:28
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class CustomerDO extends BaseDO {

	/**
	 * 	cellphone 手机号码
	 */
	private String cellphone;

	/**
	 * 	nickname 昵称
	 */
	private String nickname;
}
