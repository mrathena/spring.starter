package com.mrathena.common.toolkit;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * @author mrathena on 2019/5/27 11:34
 */
@Slf4j
public class Md5Kit {

	public static void main(String[] args) {
		System.out.println(Md5Kit.getMD5("胡诗瑞"));
	}

	private Md5Kit() {}

	private static final String MD5 = "MD5";
	private static final String CHARSET = "UTF-8";

	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	private static MessageDigest MD = null;

	static {
		try {
			MD = MessageDigest.getInstance(MD5);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static String getMD5(String string) {
		return getMD5(string.getBytes());
	}

	public static String getMD5(byte[] bytes) {
		MD.update(bytes);
		return bufferToHex(MD.digest());
	}

	private static String bufferToHex(byte[] bytes) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte[] bytes, int m, int n) {
		StringBuilder stringBuilder = new StringBuilder(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			char c0 = HEX_DIGITS[(bytes[l] & 0xf0) >> 4];
			char c1 = HEX_DIGITS[bytes[l] & 0xf];
			stringBuilder.append(c0);
			stringBuilder.append(c1);
		}
		return stringBuilder.toString();
	}

}
