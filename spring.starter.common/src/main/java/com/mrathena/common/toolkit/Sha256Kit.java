package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ServiceException;

import java.security.MessageDigest;

/**
 * @author mrathena
 */
public final class Sha256Kit {

	public static void main(String[] args) {
		System.out.println(Sha256Kit.getSha256("mrathena"));
	}

	private Sha256Kit() {}

	private static final String SHA_256 = "SHA-256";

	public static String getSha256(String string) {
		return getSha256(string.getBytes());
	}

	public static String getSha256(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance(SHA_256);
			md.update(bytes);
			return bytesToHexString(md.digest());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private static String bytesToHexString(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder();
		for (byte b : bytes) {
			int v = b & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

}
