package com.mrathena.common.exception;

import lombok.Getter;

/**
 * @author mrathena on 2019/5/27 10:10
 */
@Getter
public final class ServiceException extends RuntimeException {

	private String code;

	public ServiceException(ExceptionCode exceptionCode) {
		super(exceptionCode.getDesc());
		this.code = exceptionCode.name();
	}

	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(String message) {
		super(message);
		this.code = ExceptionCode.ERROR.name();
	}

}
