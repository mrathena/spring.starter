package com.mrathena.common.exception;

import lombok.Getter;

/**
 * 远程调用异常类
 * <p>
 * 约定:所有RPC服务调用的封装,如果出了异常,只能报该类异常
 *
 * @author mrathena on 2019/7/18 15:59
 */
@Getter
public final class RemoteServiceException extends RuntimeException {

	private String code;
	private String description;

	/**
	 * 用于包装其他异常, 可以拿到原异常的完整信息
	 */
	private RemoteServiceException(Throwable throwable) {
		super(throwable.getMessage(), throwable);
		if (throwable instanceof RemoteServiceException) {
			RemoteServiceException exception = (RemoteServiceException) throwable;
			this.code = exception.getCode();
			this.description = exception.getDescription();
		} else {
			if (ExceptionHandler.isDubboUnavailableException(throwable)) {
				this.code = ExceptionCode.REMOTE_SERVICE_UNAVAILABLE.name();
			} else if (ExceptionHandler.isDubboTimeoutException(throwable)) {
				this.code = ExceptionCode.REMOTE_SERVICE_INVOKE_TIMEOUT.name();
			} else {
				this.code = ExceptionCode.REMOTE_SERVICE_INVOKE_FAILURE.name();
			}
			this.description = ExceptionHandler.getStackTrace(throwable);
		}

	}

	/**
	 * 用于起始异常的主动抛出
	 * Unavailable和Timeout两种异常都是通过传入Throwable判断出来的,主动抛出的都是Failure
	 */
	private RemoteServiceException(String message, String description) {
		super(message);
		this.code = ExceptionCode.REMOTE_SERVICE_INVOKE_FAILURE.name();
		this.description = description;
	}

	/**
	 * 判断RemoteServiceException是否是不可用异常
	 * ExceptionHandler#isDubboUnavailableException(java.lang.Exception)
	 */
	public boolean isUnavailable() {
		return ExceptionCode.REMOTE_SERVICE_UNAVAILABLE.name().equals(code);
	}

	/**
	 * 判断RemoteServiceException是否是超时异常
	 * ExceptionHandler#isDubboTimeoutException(java.lang.Exception)
	 */
	public boolean isTimeout() {
		return ExceptionCode.REMOTE_SERVICE_INVOKE_TIMEOUT.name().equals(code);
	}

	/**
	 * 判断RemoteServiceException是否是失败异常
	 */
	public boolean isFailure() {
		return ExceptionCode.REMOTE_SERVICE_INVOKE_FAILURE.name().equals(code);
	}

}
