package com.mrathena.common.exception;

import lombok.Getter;

/**
 * @author mrathena on 2019/5/27 10:10
 */
@Getter
public final class ServiceException extends RuntimeException {

	private String code;

	/**
	 * 用于包装其他异常, 可以拿到原异常的完整信息
	 */
	public ServiceException(Throwable cause, String code, String message) {
		super(message, cause);
		this.code = code;
	}

	public ServiceException(Throwable cause, String message) {
		this(cause, ExceptionCode.ERROR.name(), message);
	}

	/**
	 * 用于起始异常的主动抛出
	 */
	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(ExceptionCode exceptionCode) {
		this(exceptionCode.name(), exceptionCode.getDesc());
	}

	public ServiceException(String message) {
		this(ExceptionCode.ERROR.name(), message);
	}

}
