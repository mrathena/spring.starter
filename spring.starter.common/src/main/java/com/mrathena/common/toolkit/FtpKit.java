package com.mrathena.common.toolkit;

import com.mrathena.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * FTP工具类
 * <p>
 * 提供单个文件的上传下载功能
 * cd,ls,rm,rmdir等命令可通过ChannelSftp直接执行, 无需开发对应功能
 * 注意:
 * 1.针对所有远程路径的操作都需要对路径以ISO-8859-1编码,这样才能支持中文路径
 * =============================================================================================
 * String filename = new String(remoteFileAbsolutePath.getBytes(), StandardCharsets.ISO_8859_1);
 * =============================================================================================
 *
 * @author mrathena on 2019/5/27 11:44
 */
@Slf4j
public final class FtpKit {

	public static void main(String[] args) {
		FTPClient ftp = FtpKit.connect("172.17.45.20", 21, "redbagftp", "redbagftp78@78F");
		FtpKit.upload(ftp, new File("C:\\Users\\mrathena\\Desktop\\java开发手册[华山版][1.5.0].pdf"), "/upload");
		FtpKit.download(ftp, "/upload/java开发手册[华山版][1.5.0].pdf", new File("C:\\Users\\mrathena\\Desktop\\aaaaaaa.pdf"));
		FtpKit.delete(ftp, "/upload/java开发手册[华山版][1.5.0].pdf");
		FtpKit.close(ftp);
	}

	private FtpKit() {}

	/**
	 * 连接FTP服务器,获取FTP连接通道
	 *
	 * @param host     主机
	 * @param port     端口
	 * @param username 用户名
	 * @param password 密码
	 */
	public static FTPClient connect(String host, int port, String username, String password) {
		log.debug("FTP: Connecting ftp server {}:{}", host, port);
		try {
			FTPClient ftp = new FTPClient();
			ftp.setConnectTimeout(1000 * 3);
			ftp.connect(host, port);
			log.debug("FTP: Connected");
			if (!ftp.login(username, password)) {
				log.debug("FTP: Login unsuccessful");
				ftp.disconnect();
				log.debug("FTP: Disconnected");
				throw new ServiceException("Invalid username or password", "连不上服务器,可能是账号密码不对");
			}
			log.debug("FTP: Logged in");
			log.debug("FTP: Get FTPClient successful");
			return ftp;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 上传文件,会覆盖原文件
	 *
	 * @param ftp                         ftp
	 * @param localFileAbsolutePath       本地文件绝对路径
	 * @param remoteFileName              远程文件名称
	 * @param remoteDirectoryAbsolutePath 远程目录绝对路径
	 */
	public static void upload(FTPClient ftp, File localFileAbsolutePath, String remoteFileName, String remoteDirectoryAbsolutePath) {
		try {
			String encodeRemoteDirectoryAbsolutePath = new String(remoteDirectoryAbsolutePath.getBytes(), StandardCharsets.ISO_8859_1);
			if (!ftp.changeWorkingDirectory(encodeRemoteDirectoryAbsolutePath)) {
				throw new ServiceException("change working directory unsuccessful, perhaps directory not exist", "切换工作路径失败,可能是目标路径不存在");
			}
			ftp.setBufferSize(1024 * 1024 * 4);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			try (InputStream inputStream = new FileInputStream(localFileAbsolutePath)) {
				String encodeRemoteFileName = new String(remoteFileName.getBytes(), StandardCharsets.ISO_8859_1);
				if (!ftp.storeFile(encodeRemoteFileName, inputStream)) {
					throw new ServiceException("upload file unsuccessful, perhaps no permission", "上传文件失败,可能是没有权限");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 上传文件,会覆盖原文件
	 *
	 * @param ftp                         ftp
	 * @param localFileAbsolutePath       本地文件绝对路径
	 * @param remoteDirectoryAbsolutePath 远程目录绝对路径
	 */
	public static void upload(FTPClient ftp, File localFileAbsolutePath, String remoteDirectoryAbsolutePath) {
		upload(ftp, localFileAbsolutePath, localFileAbsolutePath.getName(), remoteDirectoryAbsolutePath);
	}

	/**
	 * 下载文件,会覆盖原文件
	 *
	 * @param ftp                    ftp
	 * @param remoteFileAbsolutePath 远程文件绝对路径
	 * @param localFileAbsolutePath  本地文件绝对路径
	 */
	private static void download(FTPClient ftp, String remoteFileAbsolutePath, File localFileAbsolutePath) {
		try {
			boolean localFileExist = localFileAbsolutePath.exists();
			try (OutputStream outputStream = new FileOutputStream(localFileAbsolutePath)) {
				String filename = new String(remoteFileAbsolutePath.getBytes(), StandardCharsets.ISO_8859_1);
				if (!ftp.retrieveFile(filename, outputStream)) {
					// 如果localFileAbsolutePath原本不存在,则删除该文件
					if (!localFileExist) {
						try {
							outputStream.close();
							if (!localFileAbsolutePath.delete()) {
								log.debug("FTP: delete temp file unsuccessful");
							}
						} catch (Exception e) {
							log.debug("FTP: delete temp file unsuccessful");
						}
					}
					throw new ServiceException("download file unsuccessful, perhaps file not exist", "下载文件失败,可能是要目标文件不存在");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param ftp                    ftp
	 * @param remoteFileAbsolutePath 远程文件绝对路径
	 */
	private static void delete(FTPClient ftp, String remoteFileAbsolutePath) {
		try {
			String filename = new String(remoteFileAbsolutePath.getBytes(), StandardCharsets.ISO_8859_1);
			if (!ftp.deleteFile(filename)) {
				throw new ServiceException("delete file unsuccessful, perhaps no permission or file not exist", "删除文件失败,可能是没有权限或目标文件不存在");
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 关闭FTP连接
	 *
	 * @param ftp ftp
	 */
	public static void close(FTPClient ftp) {
		try {
			if (ftp.isConnected()) {
				if (!ftp.logout()) {
					throw new ServiceException("ftp close unsuccessful, perhaps ftp being occupied", "关闭连接失败,可能是连接在占用中");
				}
				ftp.disconnect();
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
