package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * @author mrathena on 2019/5/27 11:19
 */
@Slf4j
public final class IpKit {

	public static void main(String[] args) {
		System.out.println(IpKit.getIp());
	}

	private IpKit() {}

	public static String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			String message = ExceptionHandler.getClassAndMessage(e);
			log.error(message, e);
			throw new ServiceException(message);
		}
	}

}
