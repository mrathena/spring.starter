package com.mrathena.common.toolkit;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

/**
 * @author mrathena on 2019/5/27 11:44
 */
@Slf4j
public class SftpKit {

	public static void main(String[] args) {
		// TODO
		try {
			ChannelSftp sftp = SftpKit.connect("58.213.97.77", 22, "redbag_agencyrebate", "wpxikpIXg46i");
			sftp.cd("/");
			Vector<ChannelSftp.LsEntry> ls = sftp.ls("upload/deposit.rebate");
			System.out.println(ls.size());
			ls.forEach(item -> System.out.println(item.getLongname()));
			ls.forEach(item -> System.out.println(item.getFilename()));
			sftp.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SftpKit() {}

	private static final String CURRENT_PATH = ".";
	private static final String UP_PATH = "..";

	/**
	 * 连接sftp服务器
	 *
	 * @param host     主机
	 * @param port     端口
	 * @param username 用户名
	 * @param password 密码
	 */
	public static ChannelSftp connect(String host, int port, String username, String password) {
		log.info("开始连接SFTP服务器");
		ChannelSftp sftp;
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(username, host, port);
			session.setPassword(password);
			Properties sshConfig = new Properties();
			// 绕过SSH公钥检查, 因为严格的SSH公钥检查会破坏一些依赖SSH协议的自动化任务
			sshConfig.put("StrictHostKeyChecking", "no");
			session.setConfig(sshConfig);
			session.connect();
			log.info("Session connected");
			Channel channel = session.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			log.info("Connected to {}", host);
		} catch (Exception e) {
			log.error("Get sftp channel error", e);
			throw new RuntimeException(e);
		}
		return sftp;
	}

	/**
	 * 列出SFTP服务器指定目录下的所有文件
	 */
	private static Vector listFiles(ChannelSftp sftp, String directory) {
		try {
			return sftp.ls(directory);
		} catch (SftpException e) {
			log.error("List files error");
			throw new RuntimeException(e);
		}
	}

	/**
	 * 上传单个文件到SFTP服务器的指定目录
	 *
	 * @param sftp       通道
	 * @param sourceFile 要上传的本地文件
	 * @param directory  SFTP服务器接收文件的目录
	 */
	public static void upload(ChannelSftp sftp, File sourceFile, String directory) {
		// 判断文件是否存在
		if (sourceFile == null || !sourceFile.exists() || !sourceFile.isFile()) {
			throw new RuntimeException("Illegal Argument: SourceFile");
		}
		try {
			// 切换工作路径
			sftp.cd(directory);
			// 上传文件
			try (InputStream is = new FileInputStream(sourceFile)) {
				sftp.put(is, sourceFile.getName());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从InputStream中获取文件
	 *
	 * @param targetFile 目标文件,就是下载下来后就变成了这个文件
	 */
	private static void download(InputStream is, File targetFile) {
		// 判断目标文件
		if (targetFile == null || targetFile.isDirectory()) {
			throw new RuntimeException("Illegal Argument: targetFile");
		}
		// 若本地已存在该文件,直接覆盖重新下载
		if (targetFile.exists()) {
			targetFile.delete();
		}
		// 下载文件
		try (BufferedInputStream bis = new BufferedInputStream(is);
			 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile))
		) {
			int len;
			byte[] buffer = new byte[4096];
			while ((len = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 本方法支持下载ftp上的一个文件或者一个目录下载所有文件到本地目录下
	 *
	 * @param sftp                      .
	 * @param sftpServerDirectoryOrFile .
	 * @param localDirectory            .
	 * @param filePrefix                .
	 * @return .
	 */
	public static Map<String, List<String>> downloadFileOrFiles(ChannelSftp sftp, String sftpServerDirectoryOrFile, File localDirectory, String filePrefix) {
		// 判断本地目录是否存在
		if (localDirectory == null || !localDirectory.exists() || !localDirectory.isDirectory()) {
			log.error("Illegal local directory");
			throw new IllegalArgumentException("Illegal local directory");
		}
		boolean isFile = false;
		// 判断要下载的是一个文件还是目录
		String sourceFileName = sftpServerDirectoryOrFile.substring(sftpServerDirectoryOrFile.lastIndexOf("/") + 1);
		if (StringUtils.isNotBlank(filePrefix) || sftpServerDirectoryOrFile.lastIndexOf(".") != -1) {
			// 说明是个文件，前者判断不为空说明是个无后缀类型的文件，后者判断为true说明是个有后缀类型的文件
			// 将sftpServerDirectoryOrFile设置为该文件的目录
			sftpServerDirectoryOrFile = sftpServerDirectoryOrFile.substring(0, sftpServerDirectoryOrFile.lastIndexOf("/"));
			isFile = true;
		}

		// 切换工作路径
		try {
			sftp.cd(sftpServerDirectoryOrFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 列出服务器目录下的文件
		Vector ls = listFiles(sftp, sftpServerDirectoryOrFile);
		// 遍历下载文件
		try {
			Map<String, List<String>> fileNameMap = new HashMap<>();
			List<String> fileNameList = new ArrayList<>();
			List<String> fileNameFailList = new ArrayList<>();
			if (ls.isEmpty()) {
				log.info("Not fount files at sftp server directory");
				return null;
			}
			for (Object object : ls) {
				ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) object;
				String filename = entry.getFilename();
				if (filename.equals(CURRENT_PATH) || filename.equals(UP_PATH)) {
					continue;
				}
				// 判断当前项是目录还是文件
				if (!entry.getAttrs().isDir()) {
					if (isFile && !StringUtils.equalsIgnoreCase(filename, sourceFileName)) {
						// 如果传过来的路径是文件，则只下载这一个文件,不是这个文件就跳过
						continue;
					}
					// 下载文件
					InputStream is = sftp.get(filename);
					File file = new File(localDirectory, filename);
					download(is, file);
				}
			}
			fileNameMap.put("SUCCESS", fileNameList);
			fileNameMap.put("FAIL", fileNameFailList);
			return fileNameMap;
		} catch (SftpException e) {
			log.error("File download error：" + e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除目录下的文件
	 *
	 * @param sftp      通道
	 * @param directory 要删除文件所在目录
	 */
	public static void delete(ChannelSftp sftp, String directory) {
		try {
			// 切换工作路径
			sftp.cd(directory);
			// 列出当前目录下的所有目录和文件
			Vector ls = sftp.ls(directory);
			// 判断当前目录是否为空目录()
			for (Object object : ls) {
				ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) object;
				String filename = entry.getFilename();
				if (filename.equals(CURRENT_PATH) || filename.equals(UP_PATH)) {
					continue;
				}
				// 判断当前项是目录还是文件
				if (!entry.getAttrs().isDir()) {
					// 删除文件
					sftp.rm(filename);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除目录下的所有文件或者指定文件
	 *
	 * @param sftp     通道
	 * @param filePath 要删除文件所在目录或者要删除文件的路径
	 */
	public static boolean deleteFileOrFiles(ChannelSftp sftp, String filePath, String filePrefix) {
		try {
			// 判断要删除的是一个文件还是目录
			boolean isFile = false;
			String sourceFileName = filePath.substring(filePath.lastIndexOf("/") + 1);
			if (StringUtils.isNotBlank(filePrefix) || filePath.lastIndexOf(".") != -1) {
				// 说明是个文件，前者判断为空说明是个无后缀类型的文件，后者判断为true说明是个有后缀类型的文件
				// 将sftpServerDirectoryOrFile设置为该文件的目录
				filePath = filePath.substring(0, filePath.lastIndexOf("/"));
				isFile = true;
			}
			// 切换工作路径
			try {
				sftp.cd("/");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			// 列出服务器目录下的文件
			Vector ls = listFiles(sftp, filePath);

			if (ls.isEmpty()) {
				log.info("Not fount files at sftp server directory");
				return false;
			}
			for (Object object : ls) {
				ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) object;
				String filename = entry.getFilename();
				if (filename.equals(CURRENT_PATH) || filename.equals(UP_PATH)) {
					continue;
				}
				// 如果要删除的是文件，并且就是当前文件，则直接删除文件并跳出循环
				if (isFile && StringUtils.equalsIgnoreCase(sourceFileName, filename)) {
					sftp.rm(filename);
					break;
				}
				// 如果当前项是文件，且要删除的项是按目录删除，则删除该文件
				if (!entry.getAttrs().isDir() && !isFile) {
					// 删除文件
					sftp.rm(filename);
				}
			}
			return true;
		} catch (SftpException e) {
			log.error("SFTP文件删除报错：" + e);
			return false;
		}
	}

	/**
	 * 关闭通道和会话
	 *
	 * @param sftp 通道
	 */
	public static void close(ChannelSftp sftp) {
		try {
			if (sftp != null) {
				Session session = sftp.getSession();
				if (sftp.isConnected()) {
					sftp.disconnect();
				}
				if (session != null && session.isConnected()) {
					session.disconnect();
				}
			}
		} catch (JSchException e) {
			throw new RuntimeException(e);
		}
	}

}
