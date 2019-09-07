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
	 * 私有化构造器,防止瞎用
	 */
	private RemoteServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * 超时异常
	 */
	public static RemoteServiceException timeout(String message) {
		return new RemoteServiceException(ExceptionCode.REMOTE_SERVICE_INVOKE_TIMEOUT.name(), message);
	}

	/**
	 * 超时异常
	 */
	public static RemoteServiceException timeout() {
		return timeout(ExceptionCode.REMOTE_SERVICE_INVOKE_TIMEOUT.getDesc());
	}

	/**
	 * 不可用异常
	 */
	public static RemoteServiceException unavailable(String message) {
		return new RemoteServiceException(ExceptionCode.REMOTE_SERVICE_UNAVAILABLE.name(), message);
	}

	/**
	 * 不可用异常
	 */
	public static RemoteServiceException unavailable() {
		return unavailable(ExceptionCode.REMOTE_SERVICE_UNAVAILABLE.getDesc());
	}

	/**
	 * 其他异常
	 */
	public static RemoteServiceException failure(String message) {
		return new RemoteServiceException(ExceptionCode.REMOTE_SERVICE_INVOKE_FAILURE.name(), message);
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
