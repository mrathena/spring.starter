package com.mrathena.common.exception;

import lombok.Getter;

/**
 * code:异常码
 * message:给用户看的信息
 * description: RootCauseDescription
 * <p>
 * 要求message和description同时出现
 *
 * @author mrathena on 2019/5/27 10:10
 */
@Getter
public class ServiceException extends RuntimeException {

	private String code;
	private String description;

	public ServiceException(Throwable throwable) {
		super(throwable.getMessage(), throwable);
		if (throwable instanceof ServiceException) {
			ServiceException exception = (ServiceException) throwable;
			this.code = exception.getCode();
			this.description = exception.getDescription();
		} else if (throwable instanceof RemoteServiceException) {
			RemoteServiceException exception = (RemoteServiceException) throwable;
			this.code = exception.getCode();
			this.description = exception.getDescription();
		} else {
			this.code = ExceptionCode.EXCEPTION.name();
			this.description = ExceptionHandler.getRootCauseStackTrace(throwable);
		}
	}

	/**
	 * 用于起始异常的主动抛出
	 */
	public ServiceException(String code, String message, String description) {
		super(message);
		this.code = code;
		this.description = description;
	}

	public ServiceException(ExceptionCode exceptionCode) {
		this(exceptionCode.name(), exceptionCode.getMessage(), exceptionCode.getDescription());
	}

	public ServiceException(String message, String description) {
		this(ExceptionCode.EXCEPTION.name(), message, description);
	}

}
