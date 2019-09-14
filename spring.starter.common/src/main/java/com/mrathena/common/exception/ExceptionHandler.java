package com.mrathena.common.exception;

import com.alibaba.dubbo.rpc.RpcException;
import com.mrathena.common.constant.Constant;
import com.mrathena.common.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author mrathena on 2019/5/27 10:47
 */
@Slf4j
public final class ExceptionHandler {

	public static void main(String[] args) {
		try {
			throw new ServiceException(ExceptionCode.EXCEPTION);
		} catch (Exception e) {
			System.out.println(ExceptionHandler.getClassAndMessageWithoutCustomizedException(e));
			System.out.println(ExceptionHandler.getDescriptionWithoutCustomizedException(e, "你猜"));
		}
		System.out.println();
		try {
			int a = 0;
			System.out.println(10 / a);
		} catch (Exception e) {
			System.out.println(ExceptionHandler.getClassAndMessageWithoutCustomizedException(e));
			System.out.println(ExceptionHandler.getDescriptionWithoutCustomizedException(e, "你猜"));
		}
	}

	private ExceptionHandler() {}

	/**
	 * 自定义的异常类集合
	 */
	private static final Class[] CUSTOMIZED_EXCEPTION_CLASS_ARRAY =
			{ServiceException.class, RemoteServiceException.class};

	/**
	 * 获取根源异常的堆栈信息
	 */
	public static String getRootCauseStackTrace(Throwable throwable) {
		return ExceptionUtils.getStackTrace(ExceptionUtils.getRootCause(throwable));
	}

	/**
	 * 获取Exception的类名和信息
	 * java.lang.NullPointerException: null
	 * com.mrathena.common.exception.ExceptionCode: 客户不存在
	 */
	public static String getClassAndMessage(Throwable throwable) {
		return throwable.getClass().getName() + Constant.COLON + Constant.BLANK + throwable.getMessage();
	}

	/**
	 * 获取Exception的类名和信息(排除自定义的异常类的类名)
	 * java.lang.NullPointerException: null
	 * 客户不存在
	 */
	public static String getClassAndMessageWithoutCustomizedException(Throwable throwable) {
		String result = Constant.EMPTY;
		boolean isCustomizedException = false;
		for (Class customizedExceptionClass : CUSTOMIZED_EXCEPTION_CLASS_ARRAY) {
			if (customizedExceptionClass.isInstance(throwable)) {
				isCustomizedException = true;
				break;
			}
		}
		if (!isCustomizedException) {
			result += throwable.getClass().getName() + Constant.COLON + Constant.BLANK;
		}
		result += throwable.getMessage();
		return result;
	}

	/**
	 * 获取异常描述信息
	 * 获取客户信息异常[java.lang.NullPointerException: null]
	 * 获取客户信息异常[com.mrathena.common.exception.ExceptionCode: 客户不存在]
	 */
	public static String getDescription(Throwable throwable, String content) {
		return content + Constant.L_BRACKET + getClassAndMessage(throwable) + Constant.R_BRACKET;
	}

	/**
	 * 获取异常描述信息(排除自定义的异常类的类名)
	 * 获取客户信息异常[java.lang.NullPointerException: null]
	 * 获取客户信息异常[客户不存在]
	 */
	public static String getDescriptionWithoutCustomizedException(Throwable throwable, String content) {
		return content + Constant.L_BRACKET + getClassAndMessageWithoutCustomizedException(throwable) + Constant.R_BRACKET;
	}

	/**
	 * 判断异常是否为Dubbo的 timeout 异常
	 */
	public static boolean isDubboTimeoutException(Throwable throwable) {
		if (throwable instanceof RpcException) {
			RpcException rpcException = (RpcException) throwable;
			return rpcException.isTimeout();
		}
		return false;
	}

	/**
	 * 判断异常是否为Dubbo的 no provider available 异常
	 */
	public static boolean isDubboUnavailableException(Throwable throwable) {
		if (throwable instanceof RpcException) {
			RpcException rpcException = (RpcException) throwable;
			return rpcException.getMessage().contains("No provider available");
		}
		return false;
	}

	/**
	 * 统一处理异常
	 * return ExceptionHandler.handleBizException(e);
	 */
	public static <T> Response<T> handleBizException(Throwable throwable) {
		Response<T> response = new Response<>();
		if (throwable instanceof IllegalArgumentException) {
			response.setCode(ExceptionCode.ILLEGAL_ARGUMENT.name());
			response.setMessage(throwable.getMessage());
		} else if (throwable instanceof ServiceException) {
			ServiceException serviceException = (ServiceException) throwable;
			response.setCode(serviceException.getCode());
			response.setMessage(serviceException.getMessage());
		} else if (throwable instanceof RemoteServiceException) {
			RemoteServiceException remoteServiceException = (RemoteServiceException) throwable;
			response.setCode(remoteServiceException.getCode());
			response.setMessage(remoteServiceException.getMessage());
		} else {
			response.setCode(ExceptionCode.EXCEPTION.name());
			response.setMessage(ExceptionCode.EXCEPTION.getMessage());
		}
		return response;
	}

}
