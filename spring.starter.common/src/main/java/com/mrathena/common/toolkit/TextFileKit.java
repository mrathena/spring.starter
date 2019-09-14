package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ServiceException;
import lombok.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mrathena on 2018/12/24 15:00
 */
public final class TextFileKit {

	public static void main(String[] args) {
		TextFileKit.fileHandleInBatch(new File("C:\\Users\\mrathena\\Desktop/redis删除.txt"), 1, list -> {
			try {
				List<String> result = new LinkedList<>();
				System.out.println(list.get(0).split(" ").length);
				String[] strings = list.get(0).split(" ");
				for (String string : strings) {
					result.add("del " + string);
				}
				TextFileKit.collectionToFile(result, string -> string, new File("C:\\Users\\mrathena\\Desktop\\result"), true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private TextFileKit() {}

	private static final int DEFAULT_BATCH_SIZE = 1000;

	/**
	 * 获取文本文件的总行数
	 */
	public static int count(File file) {
		try {
			CheckKit.needExist(file, "file");
			try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
				reader.skip(file.length());
				return reader.getLineNumber() + 1;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 单文本文件转LinkedList
	 */
	public static <T> List<T> fileToLinkedList(File file,
	                                           Filter filter,
	                                           StringToTypeConverter<T> converter) {
		try {
			CheckKit.needIsFile(file, "file");
			CheckKit.needNotNull(filter, "filter");
			CheckKit.needNotNull(converter, "converter");
			List<T> list = new LinkedList<>();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (filter.isValid(line)) {
						list.add(converter.convert(line));
					}
				}
			}
			return list;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 单文本文件转LinkedList
	 */
	public static <T> List<T> fileToLinkedList(File file,
	                                           StringToTypeConverter<T> converter) {
		return fileToLinkedList(file, line -> true, converter);
	}

	/**
	 * 单文本文件转LinkedList
	 */
	public static List<String> fileToLinkedList(File file,
	                                            Filter filter) {
		return fileToLinkedList(file, filter, line -> line);
	}

	/**
	 * 单文本文件转LinkedList
	 */
	public static List<String> fileToLinkedList(File file) {
		return fileToLinkedList(file, line -> true, line -> line);
	}

	/**
	 * 多文本文件转LinkedList
	 * 提供一个目录,把该目录下的所有直接文件都转成List(非递归)
	 */
	public static <T> List<T> multiFileToLinkedList(File directory,
	                                                Filter filter,
	                                                StringToTypeConverter<T> converter) {
		try {
			CheckKit.needDirectoryHasFile(directory, "directory");
			CheckKit.needNotNull(filter, "filter");
			CheckKit.needNotNull(converter, "converter");
			List<T> list = new LinkedList<>();
			File[] fileArray = directory.listFiles();
			assert fileArray != null;
			List<File> fileList = Arrays.stream(fileArray).filter(File::isFile).collect(Collectors.toList());
			for (File file : fileArray) {
				list.addAll(fileToLinkedList(file, filter, converter));
			}
			return list;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 多文本文件转LinkedList
	 * 提供一个目录,把该目录下的所有直接文件都转成List(非递归)
	 */
	public static <T> List<T> multiFileToLinkedList(File directory,
	                                                StringToTypeConverter<T> converter) {
		return multiFileToLinkedList(directory, line -> true, converter);
	}

	/**
	 * 多文本文件转LinkedList
	 * 提供一个目录,把该目录下的所有直接文件都转成List(非递归)
	 */
	public static List<String> multiFileToLinkedList(File directory,
	                                                 Filter filter) {
		return multiFileToLinkedList(directory, filter, line -> line);
	}

	/**
	 * 多文本文件转LinkedList
	 * 提供一个目录,把该目录下的所有直接文件都转成List(非递归)
	 */
	public static List<String> multiFileToLinkedList(File directory) {
		return multiFileToLinkedList(directory, line -> true, line -> line);
	}

	/**
	 * 单文本文件转HashMap
	 */
	public static <K, V> Map<K, V> fileToHashMap(File file,
	                                             Filter filter,
	                                             EntityConverter<K, V> converter) {
		try {
			CheckKit.needIsFile(file, "file");
			CheckKit.needNotNull(filter, "filter");
			CheckKit.needNotNull(converter, "converter");
			Map<K, V> map = new HashMap<>(count(file));
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (filter.isValid(line)) {
						Entry<K, V> entry = converter.convert(line);
						map.put(entry.getKey(), entry.getValue());
					}
				}
			}
			return map;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 单文本文件转HashMap
	 */
	public static <K, V> Map<K, V> fileToHashMap(File file,
	                                             EntityConverter<K, V> converter) {
		return fileToHashMap(file, line -> true, converter);
	}

	/**
	 * 单文本文件行分批处理
	 */
	public static <T> void fileHandleInBatch(File file,
	                                         Filter filter,
	                                         StringToTypeConverter<T> converter,
	                                         int batchSize,
	                                         Handler<T> handler) {
		try {
			CheckKit.needIsFile(file, "file");
			CheckKit.needNotNull(filter, "filter");
			CheckKit.needNotNull(converter, "converter");
			CheckKit.needNotNull(handler, "handler");
			if (batchSize <= 0) {
				throw new IllegalArgumentException("batchSize must greater than 0");
			}
			List<T> list = new LinkedList<>();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (filter.isValid(line)) {
						list.add(converter.convert(line));
						if (list.size() == batchSize) {
							handler.handle(list);
							list.clear();
						}
					}
				}
				if (list.size() > 0) {
					handler.handle(list);
					list.clear();
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 单文本文件行分批处理
	 */
	public static <T> void fileHandleInBatch(File file,
	                                         StringToTypeConverter<T> converter,
	                                         int batchSize,
	                                         Handler<T> handler) {
		fileHandleInBatch(file, line -> true, converter, batchSize, handler);
	}

	/**
	 * 单文本文件行分批处理
	 */
	public static void fileHandleInBatch(File file,
	                                     Filter filter,
	                                     int batchSize,
	                                     Handler<String> handler) {
		fileHandleInBatch(file, filter, line -> line, batchSize, handler);
	}

	/**
	 * 单文本文件行分批处理
	 */
	public static <T> void fileHandleInBatch(File file,
	                                         Filter filter,
	                                         StringToTypeConverter<T> converter,
	                                         Handler<T> handler) {
		fileHandleInBatch(file, filter, converter, DEFAULT_BATCH_SIZE, handler);
	}

	/**
	 * 单文本文件行分批处理
	 */
	public static void fileHandleInBatch(File file,
	                                     int batchSize,
	                                     Handler<String> handler) {
		fileHandleInBatch(file, line -> true, line -> line, batchSize, handler);
	}

	/**
	 * 单文本文件行分批处理
	 */
	public static <T> void fileHandleInBatch(File file,
	                                         StringToTypeConverter<T> converter,
	                                         Handler<T> handler) {
		fileHandleInBatch(file, line -> true, converter, DEFAULT_BATCH_SIZE, handler);
	}

	/**
	 * 单文本文件行分批处理
	 */
	public static void fileHandleInBatch(File file,
	                                     Filter filter,
	                                     Handler<String> handler) {
		fileHandleInBatch(file, filter, line -> line, DEFAULT_BATCH_SIZE, handler);
	}

	/**
	 * 单文本文件行分批处理
	 */
	public static void fileHandleInBatch(File file,
	                                     Handler<String> handler) {
		fileHandleInBatch(file, line -> true, line -> line, DEFAULT_BATCH_SIZE, handler);
	}

	/**
	 * collection转成文本文件
	 */
	public static <T> void collectionToFile(Collection<T> collection,
	                                        TypeToStringConverter<T> converter,
	                                        File file,
	                                        boolean append) {
		try {
			CheckKit.needNotNullOrEmpty(collection, "list");
			CheckKit.needNotNull(converter, "converter");
			CheckKit.needNotNull(file, "file");
			if (!file.exists()) {
				if (!file.createNewFile()) {
					throw new ServiceException("create new file failure", "创建文件失败,可能是没有权限");
				}
			} else {
				CheckKit.needIsFile(file, "file");
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
				for (T t : collection) {
					writer.write(converter.convert(t));
					writer.newLine();
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@FunctionalInterface
	public interface Filter {
		/**
		 * 判断line是否有效
		 *
		 * @param line .
		 * @return .
		 */
		boolean isValid(String line);
	}

	@FunctionalInterface
	public interface StringToTypeConverter<T> {
		/**
		 * line转换为T
		 *
		 * @param line .
		 * @return .
		 */
		T convert(String line);
	}

	@FunctionalInterface
	public interface TypeToStringConverter<T> {
		/**
		 * T转换为string
		 *
		 * @param object .
		 * @return .
		 */
		String convert(T object);
	}

	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Entry<K, V> {
		private K key;
		private V value;
	}

	@FunctionalInterface
	public interface EntityConverter<K, V> {
		/**
		 * line转Entity<K, V>
		 *
		 * @param line .
		 * @return .
		 */
		Entry<K, V> convert(String line);
	}

	@FunctionalInterface
	public interface Handler<T> {
		/**
		 * 批量处理list
		 *
		 * @param list .
		 */
		void handle(List<T> list);
	}

}