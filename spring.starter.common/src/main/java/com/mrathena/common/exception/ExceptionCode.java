package com.mrathena.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mrathena on 2019/5/27 10:19
 */
@Getter
@AllArgsConstructor
public enum ExceptionCode {

	/**
	 * 错误码
	 */
	ERROR("异常"),
	COMMON_ERROR("COMMON异常"),
	DAO_ERROR("DAO异常"),
	INTEGRATION_ERROR("INTEGRATION异常"),
	API_ERROR("API异常"),
	BUSINESS_ERROR("BUSINESS异常"),
	SERVICE_ERROR("SERVICE异常"),
	TASK_ERROR("TASK异常"),
	WEB_ERROR("WEB异常"),
	ILLEGAL_ARGUMENT("非法参数"),
	ILLEGAL_STATUS("非法状态"),
	REMOTE_SERVICE_UNAVAILABLE("远程服务不可用"),
	REMOTE_SERVICE_INVOKE_TIMEOUT("远程服务调用超时"),
	REMOTE_SERVICE_INVOKE_FAILURE("远程服务调用失败"),
	REDIS_LOCK_CONFLICT("分布式锁冲突"),
	DATA_NOT_FOUND("数据未找到"),
	;

	private String desc;

}
