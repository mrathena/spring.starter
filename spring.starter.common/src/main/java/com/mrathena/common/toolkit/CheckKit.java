package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ExceptionCode;
import com.mrathena.common.exception.ServiceException;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mrathena on 2019/8/13 13:36
 */
public final class CheckKit {

	private CheckKit() {}

	/**
	 * 校验Object不为null
	 */
	public static void needNotNull(Object argument, String name) {
		if (null == argument) {
			throw new ServiceException(ExceptionCode.ILLEGAL_ARGUMENT.name(), name + " must not null");
		}
	}

	/**
	 * 校验String不为null或empty
	 */
	public static void needNotNullOrEmpty(String argument, String name) {
		needNotNull(argument, name);
		if (argument.isEmpty()) {
			throw new ServiceException(ExceptionCode.ILLEGAL_ARGUMENT.name(), name + " must not null or empty");
		}
	}

	/**
	 * 校验Collection不为null或empty
	 */
	public static void needNotNullOrEmpty(Collection argument, String name) {
		needNotNull(argument, name);
		if (argument.isEmpty()) {
			throw new ServiceException(ExceptionCode.ILLEGAL_ARGUMENT.name(), name + " must not null or empty");
		}
	}

	/**
	 * 校验Map不为null或empty
	 */
	public static void needNotNullOrEmpty(Map argument, String name) {
		needNotNull(argument, name);
		if (argument.isEmpty()) {
			throw new ServiceException(ExceptionCode.ILLEGAL_ARGUMENT.name(), name + " must not null or empty");
		}
	}

	/**
	 * 校验文件/目录必须存在
	 */
	public static void needExist(File argument, String name) {
		needNotNull(argument, name);
		if (!argument.exists()) {
			throw new ServiceException(ExceptionCode.ILLEGAL_ARGUMENT.name(), name + " must exist");
		}
	}

	/**
	 * 校验File是文件
	 */
	public static void needIsFile(File argument, String name) {
		needExist(argument, name);
		if (!argument.isFile()) {
			throw new ServiceException(ExceptionCode.ILLEGAL_ARGUMENT.name(), name + " must be file");
		}
	}

	/**
	 * 校验File是目录
	 */
	public static void needIsDirectory(File argument, String name) {
		needExist(argument, name);
		if (!argument.isDirectory()) {
			throw new ServiceException(ExceptionCode.ILLEGAL_ARGUMENT.name(), name + " must be directory");
		}
	}

	/**
	 * 校验目录不空(有子目录/文件)
	 */
	public static void needDirectoryNotEmpty(File argument, String name) {
		needIsDirectory(argument, name);
		File[] fileArray = argument.listFiles();
		assert fileArray != null;
		if (fileArray.length == 0) {
			throw new ServiceException(ExceptionCode.ILLEGAL_ARGUMENT.name(), name + " must not empty");
		}
	}

	/**
	 * 校验目录有文件(排除子目录)
	 */
	public static void needDirectoryHasFile(File argument, String name) {
		needDirectoryNotEmpty(argument, name);
		File[] fileArray = argument.listFiles();
		assert fileArray != null;
		List<File> fileList = Arrays.stream(fileArray).filter(File::isFile).collect(Collectors.toList());
		if (fileList.isEmpty()) {
			throw new ServiceException(ExceptionCode.ILLEGAL_ARGUMENT.name(), "No files in the directory");
		}
	}

}
