package com.mrathena.common.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 远程调用异常类
 * <p>
 * 约定:所有RPC服务调用的封装,如果出了异常,只能报该类异常
 *
 * @author mrathena on 2019/7/18 15:59
 */
@Slf4j
@Getter
public final class RemoteServiceException extends RuntimeException {

	private String code;

	/**
	 * 用于包装其他异常, 可以拿到原异常的完整信息
	 */
	private RemoteServiceException(Exception exception, String code, String message) {
		super(message, exception);
		this.code = code;
	}

	public static RemoteServiceException unavailable(Exception exception, String message) {
		return new RemoteServiceException(exception, ExceptionCode.REMOTE_SERVICE_UNAVAILABLE.name(), message);
	}

	public static RemoteServiceException unavailable(Exception exception) {
		return unavailable(exception, ExceptionCode.REMOTE_SERVICE_UNAVAILABLE.getDesc());
	}

	public static RemoteServiceException timeout(Exception exception, String message) {
		return new RemoteServiceException(exception, ExceptionCode.REMOTE_SERVICE_INVOKE_TIMEOUT.name(), message);
	}

	public static RemoteServiceException timeout(Exception exception) {
		return timeout(exception, ExceptionCode.REMOTE_SERVICE_INVOKE_TIMEOUT.getDesc());
	}

	public static RemoteServiceException failure(Exception exception, String code, String message) {
		return new RemoteServiceException(exception, code, message);
	}

	public static RemoteServiceException failure(Exception exception, String message) {
		return failure(exception, ExceptionCode.REMOTE_SERVICE_INVOKE_FAILURE.name(), message);
	}

	/**
	 * 用于起始异常的主动抛出
	 */
	private RemoteServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	public static RemoteServiceException unavailable(String message) {
		return new RemoteServiceException(ExceptionCode.REMOTE_SERVICE_UNAVAILABLE.name(), message);
	}

	public static RemoteServiceException unavailable() {
		return unavailable(ExceptionCode.REMOTE_SERVICE_UNAVAILABLE.getDesc());
	}

	public static RemoteServiceException timeout(String message) {
		return new RemoteServiceException(ExceptionCode.REMOTE_SERVICE_INVOKE_TIMEOUT.name(), message);
	}

	public static RemoteServiceException timeout() {
		return timeout(ExceptionCode.REMOTE_SERVICE_INVOKE_TIMEOUT.getDesc());
	}

	public static RemoteServiceException failure(String code, String message) {
		return new RemoteServiceException(code, message);
	}

	public static RemoteServiceException failure(String message) {
		return failure(ExceptionCode.REMOTE_SERVICE_INVOKE_FAILURE.name(), message);
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

}
