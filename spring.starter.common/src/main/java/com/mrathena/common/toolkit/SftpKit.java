package com.mrathena.common.toolkit;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.mrathena.common.exception.ExceptionHandler;
import com.mrathena.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

/**
 * SFTP工具类
 * <p>
 * 提供单个文件的上传下载功能
 * cd,ls,rm,rmdir等命令比较简单,可通过ChannelSftp直接执行, 无需开发对应功能
 * <p>
 * 注意:
 * 1.ls在便利的时候需要做类型强转
 * 2.ls可以使用LsEntryFilter过滤掉[.][..]
 * 3.sftp.cd(); 需要绝对路径
 * =============================================================
 * Vector ls = sftp.ls("/upload/test");
 * for (Object object : ls) {
 * ----ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) object;
 * ----String filename = entry.getFilename();
 * ----...
 * }
 * =============================================================
 *
 * @author mrathena on 2019/5/27 11:44
 */
@Slf4j
public final class SftpKit {

	public static void main(String[] args) {
		try {
			ChannelSftp sftp = SftpKit.connect("58.213.97.77", 22, "redbag_agencyrebate", "wpxikpIXg46i");
			SftpKit.upload(sftp, new File("C:\\Users\\mrathena\\Desktop\\20190908.值班记录.xlsx"), "/upload/test");
			SftpKit.download(sftp, "/upload/test/20190908.值班记录.xlsx", new File("C:\\Users\\mrathena\\Desktop\\20190908.值班记录.xlsx111"));
			SftpKit.delete(sftp, "/upload/test/20190908.值班记录.xlsx");
			SftpKit.close(sftp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SftpKit() {}

	/**
	 * 连接SFTP服务器,获取SFTP连接通道
	 *
	 * @param host     主机
	 * @param port     端口
	 * @param username 用户名
	 * @param password 密码
	 */
	public static ChannelSftp connect(String host, int port, String username, String password) {
		log.debug("SFTP: Connecting sftp server {}:{}", host, port);
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
			log.debug("SFTP: Session connected");
			Channel channel = session.openChannel("sftp");
			channel.connect();
			log.debug("SFTP: Channel connected");
			sftp = (ChannelSftp) channel;
			log.debug("SFTP: Get ChannelSftp successful");
			return sftp;
		} catch (Exception e) {
			String message = ExceptionHandler.getClassAndMessage(e);
			log.error(message, e);
			throw new ServiceException(e, message);
		}
	}

	/**
	 * 上传文件,会覆盖原文件
	 *
	 * @param sftp                        sftp
	 * @param localFileAbsolutePath       本地文件绝对路径
	 * @param remoteFileName              远程文件名称
	 * @param remoteDirectoryAbsolutePath 远程目录绝对路径
	 */
	public static void upload(ChannelSftp sftp, File localFileAbsolutePath, String remoteFileName, String remoteDirectoryAbsolutePath) {
		try {
			sftp.cd(remoteDirectoryAbsolutePath);
			try (InputStream inputStream = new FileInputStream(localFileAbsolutePath)) {
				sftp.put(inputStream, remoteFileName);
			}
		} catch (Exception e) {
			String message = ExceptionHandler.getClassAndMessage(e);
			log.error(message, e);
			throw new ServiceException(e, message);
		}
	}

	/**
	 * 上传文件,会覆盖原文件
	 *
	 * @param sftp                        sftp
	 * @param localFileAbsolutePath       本地文件绝对路径
	 * @param remoteDirectoryAbsolutePath 远程目录绝对路径
	 */
	public static void upload(ChannelSftp sftp, File localFileAbsolutePath, String remoteDirectoryAbsolutePath) {
		upload(sftp, localFileAbsolutePath, localFileAbsolutePath.getName(), remoteDirectoryAbsolutePath);
	}

	/**
	 * 下载文件,会覆盖原文件
	 *
	 * @param sftp                   sftp
	 * @param remoteFileAbsolutePath 远程文件绝对路径
	 * @param localFileAbsolutePath  本地文件绝对路径
	 */
	private static void download(ChannelSftp sftp, String remoteFileAbsolutePath, File localFileAbsolutePath) {
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(sftp.get(remoteFileAbsolutePath));
			 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFileAbsolutePath))
		) {
			int len;
			byte[] buffer = new byte[4096];
			while ((len = bufferedInputStream.read(buffer)) != -1) {
				bufferedOutputStream.write(buffer, 0, len);
			}
		} catch (Exception e) {
			String message = ExceptionHandler.getClassAndMessage(e);
			log.error(message, e);
			throw new ServiceException(e, message);
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param sftp                   sftp
	 * @param remoteFileAbsolutePath 远程文件绝对路径
	 */
	private static void delete(ChannelSftp sftp, String remoteFileAbsolutePath) {
		try {
			sftp.rm(remoteFileAbsolutePath);
		} catch (Exception e) {
			String message = ExceptionHandler.getClassAndMessage(e);
			log.error(message, e);
			throw new ServiceException(e, message);
		}
	}

	/**
	 * 关闭SFTP连接
	 *
	 * @param sftp sftp
	 */
	public static void close(ChannelSftp sftp) {
		try {
			Session session = sftp.getSession();
			if (sftp.isConnected()) {
				sftp.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		} catch (Exception e) {
			String message = ExceptionHandler.getClassAndMessage(e);
			log.error(message, e);
			throw new ServiceException(e, message);
		}
	}

}
