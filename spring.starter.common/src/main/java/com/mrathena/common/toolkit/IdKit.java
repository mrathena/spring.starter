package com.mrathena.common.toolkit;

import com.mrathena.common.constant.Constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author mrathena on 2019/5/27 11:17
 */
public class IdKit {

	private IdKit() {}

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	public static String generateUUID() {
		return UUID.randomUUID().toString().replace(Constant.MINUS, Constant.EMPTY);
	}

	/**
	 * 生成32位长度的只有数字的流水号
	 */
	public static String generateSerialNo() {
		String part = LocalDateTime.now().format(FORMATTER);
		// 留下其微秒位和纳秒位, 1毫秒=1000微秒=1000000纳秒
		String part2 = String.valueOf(System.nanoTime());
		part2 = part2.substring(part2.length() - 6);
		String part3 = String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 999999999));
		return part + part2 + part3;
	}

}
