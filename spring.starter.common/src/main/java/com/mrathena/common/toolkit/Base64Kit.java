package com.mrathena.common.toolkit;

import java.util.Base64;

/**
 * @author mrathena on 2019/5/27 11:33
 */
public final class Base64Kit {

	public static void main(String[] args) {
		String mrathena = "mrathena";
		System.out.println(encodeToString(mrathena));
		System.out.println(encodeToString(mrathena.getBytes()));
		System.out.println(new String(encode(mrathena)));
		System.out.println(new String(encode(mrathena.getBytes())));
		String after = encodeToString(mrathena);
		System.out.println(decodeToString(after));
		System.out.println(decodeToString(after.getBytes()));
		System.out.println(new String(decode(after)));
		System.out.println(new String(decode(after.getBytes())));
	}

	private Base64Kit() {}

	public static String encodeToString(String string) {
		return encodeToString(string.getBytes());
	}

	public static String encodeToString(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static byte[] encode(String string) {
		return encode(string.getBytes());
	}

	public static byte[] encode(byte[] bytes) {
		return Base64.getEncoder().encode(bytes);
	}

	public static byte[] decode(String string) {
		return Base64.getDecoder().decode(string);
	}

	public static byte[] decode(byte[] bytes) {
		return Base64.getDecoder().decode(bytes);
	}

	public static String decodeToString(String string) {
		return new String(Base64.getDecoder().decode(string));
	}

	public static String decodeToString(byte[] bytes) {
		return new String(Base64.getDecoder().decode(bytes));
	}

}
