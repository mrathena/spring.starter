package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ServiceException;

import java.net.InetAddress;

/**
 * @author mrathena on 2019/5/27 11:19
 */
public final class IpKit {

	public static void main(String[] args) {
		System.out.println(IpKit.getIp());
	}

	private IpKit() {}

	public static String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
