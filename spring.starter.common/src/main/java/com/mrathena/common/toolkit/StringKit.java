package com.mrathena.common.toolkit;

import com.mrathena.common.constant.Constant;

import java.util.Optional;

/**
 * @author mrathena on 2019/5/27 11:29
 */
public class StringKit {

	private StringKit() {}

	/**
	 * 获取非空String
	 */
	public static String getNotNullString(String object) {
		return Optional.ofNullable(object).orElse(Constant.EMPTY);
	}

}
