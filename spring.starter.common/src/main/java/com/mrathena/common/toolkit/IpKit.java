package com.mrathena.common.toolkit;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author mrathena on 2019/5/27 11:19
 */
@Slf4j
public class IpKit {

	private IpKit() {}

	/**
	 * 获取当前服务器IP地址
	 */
	public static String getServerIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.error("Get ip address failure, use default [127.0.0.1]", e);
			return "127.0.0.1";
		}
	}

}
