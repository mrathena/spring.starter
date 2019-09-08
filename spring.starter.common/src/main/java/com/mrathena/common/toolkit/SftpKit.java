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
import java.util.Vector;

/**
 * SFTP工具类
 * <p>
 * 提供单个文件的上传下载功能
 * cd,ls,rm,rmdir等命令可通过ChannelSftp直接执行, 无需开发对应功能
 * <p>
 * 注意:
 * 1.ls在便利的时候需要做类型强转
 * 2.ls可以使用LsEntryFilter过滤掉[.][..]
 * =========================================================
 * Vector ls = sftp.ls("/upload/test");
 * for (Object object : ls) {
 * ----ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) object;
 * ----String filename = entry.getFilename();
 * ----...
 * }
 * =========================================================
 *
 * @author mrathena on 2019/5/27 11:44
 */
@Slf4j
public final class SftpKit {

	public static void main(String[] args) {
		// TODO
		try {
			ChannelSftp sftp = SftpKit.connect("58.213.97.77", 22, "redbag_agencyrebate", "wpxikpIXg46i");
			SftpKit.upload(sftp, new File("C:\\Users\\mrathena\\Desktop\\20190908.值班记录.xlsx"), "/upload/test");
			SftpKit.download(sftp, "/upload/test/20190908.值班记录.xlsx", new File("C:\\Users\\mrathena\\Desktop\\20190908.值班记录.xlsx111"));
			sftp.rm("/upload/test/20190908.值班记录.xlsx");


			Vector<ChannelSftp.LsEntry> ls = sftp.ls("/upload/test");
			System.out.println(ls.size());
			ls.forEach(item -> System.out.println(item.getLongname()));
			ls.forEach(item -> System.out.println(item.getFilename()));
			SftpKit.close(sftp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SftpKit() {}

	/**
	 * 连接sftp服务器,获取SFTP连接通道
	 *
	 * @param host     主机
	 * @param port     端口
	 * @param username 用户名
	 * @param password 密码
	 */
	public static ChannelSftp connect(String host, int port, String username, String password) {
		log.debug("SFTP: Try connect to sftp server {}:{}", host, port);
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
	 * 上传单个文件到SFTP服务器的指定目录, 如果文件存在则会覆盖原文件
	 *
	 * @param sftp          通道
	 * @param file          要上传的本地文件
	 * @param sftpDirectory SFTP服务器接收文件的目录, eg: "/upload/test"
	 */
	public static void upload(ChannelSftp sftp, File file, String sftpDirectory) {
		try {
			sftp.cd(sftpDirectory);
			try (InputStream is = new FileInputStream(file)) {
				sftp.put(is, file.getName());
			}
		} catch (Exception e) {
			String message = ExceptionHandler.getClassAndMessage(e);
			log.error(message, e);
			throw new ServiceException(e, message);
		}
	}

	/**
	 * 下载单个SFTP服务器文件到本地, 如果文件存在则会覆盖原文件
	 *
	 * @param sftp         通道
	 * @param sftpFilePath SFTP文件路径, eg: "/upload/test/test.csv"
	 * @param file         目标文件,就是下载下来后就变成了这个文件
	 */
	private static void download(ChannelSftp sftp, String sftpFilePath, File file) {
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(sftp.get(sftpFilePath));
			 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))
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
	 * 关闭通道和会话
	 *
	 * @param sftp 通道
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
