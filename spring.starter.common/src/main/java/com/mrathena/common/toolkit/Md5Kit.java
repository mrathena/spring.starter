package com.mrathena.common.toolkit;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author mrathena on 2019/5/27 11:34
 * <p>
 * 注意: MessageDigest 不是线程安全的, 不可以直接用成员变量, 每次使用需要重新获取实例 MessageDigest.getInstance(MD5);
 */
@Slf4j
public final class Md5Kit {

	public static void main(String[] args) throws Exception {
		String mrathena = "mrathena";
		System.out.println(Md5Kit.getMd5(mrathena));
		System.out.println(Md5Kit.getMd5(mrathena.getBytes()));
		// 并发测试
		System.out.println();
		ExecutorService executor = Executors.newFixedThreadPool(5);
		ConcurrentHashSet<String> set = new ConcurrentHashSet<>();
		for (int i = 0; i < 10; i++) {
			executor.execute(() -> set.add(Md5Kit.getMd5(mrathena)));
		}
		executor.shutdown();
		while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
			System.out.println("线程池没有关闭");
		}
		set.forEach(System.out::println);
	}

	private Md5Kit() {}

	private static final String MD5 = "MD5";
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	public static String getMd5(String string) {
		return getMd5(string.getBytes());
	}

	public static String getMd5(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance(MD5);
			md.update(bytes);
			return bufferToHex(md.digest());
		} catch (Exception e) {
			String message = ExceptionHandler.getClassAndMessage(e);
			log.error(message, e);
			throw new ServiceException(e, message);
		}
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
