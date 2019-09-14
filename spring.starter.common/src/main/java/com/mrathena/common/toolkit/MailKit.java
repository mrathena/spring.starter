package com.mrathena.common.toolkit;

import com.mrathena.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author mrathena
 */
@Slf4j
public final class MailKit {

	public static void main(String[] args) {
		String to = "747779761@qq.com";
		String content = "邮件内容 - 仅作测试, 无需回复";
		MailKit.send(to, content);
	}

	private MailKit() {}

	/**
	 * 邮件属性
	 */
	private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String MAIL_SMTP_HOST = "mail.smtp.host";
	private static final String MAIL_SMTP_PORT = "mail.smtp.port";
	private static final String MAIL_SMTP_USERNAME = "mail.smtp.username";
	private static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";

	/**
	 * 邮件属性值, AUTH_CODE:授权码
	 */
	private static final String PROTOCOL = "smtp";
	private static final String AUTH = "true";
	private static final String HOST = "smtp.163.com";
	private static final String PORT = "25";
	private static final String USERNAME = "18234089811@163.com";
	private static final String AUTH_CODE = "zxcvbnm0";

	/**
	 * 发送邮件
	 *
	 * @param properties 邮件服务器属性
	 * @param from       发送邮箱
	 * @param to         接受邮箱
	 * @param cc         抄送邮箱
	 * @param bcc        密送邮箱
	 * @param subject    邮件主题
	 * @param content    邮件内容
	 * @param attachment 附件文件列表
	 */
	public static void send(Properties properties, String from, Set<String> to, Set<String> cc, Set<String> bcc,
	                        String subject, String content, Set<String> attachment) {
		try {
			// 创建会话
			Session session = Session.getInstance(properties);
			// 创建信息
			Message message = new MimeMessage(session);
			BodyPart bodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			// 发件人
			message.setFrom(new InternetAddress(from));
			// 发送
			if (to != null && !to.isEmpty()) {
				Address[] toArray = toInternetAddress(to);
				message.setRecipients(Message.RecipientType.TO, toArray);
			}
			// 抄送
			if (cc != null && !cc.isEmpty()) {
				Address[] ccArray = toInternetAddress(cc);
				message.setRecipients(Message.RecipientType.CC, ccArray);
			}
			// 密送
			if (bcc != null && !bcc.isEmpty()) {
				Address[] bccArray = toInternetAddress(bcc);
				message.setRecipients(Message.RecipientType.BCC, bccArray);
			}
			// 发送日期
			message.setSentDate(new Date());
			// 主题
			message.setSubject(subject);
			// 内容
			content = content == null ? Constant.EMPTY : content;
			message.setText(content);
			// 显示以HTML格式的文本内容
			bodyPart.setContent(content, "text/html;charset=GBK");
			multipart.addBodyPart(bodyPart);
			// 添加多个附件
			if (attachment != null) {
				addAttachment(attachment, multipart);
			}
			message.setContent(multipart);
			// 邮件服务器进行验证
			String protocol = properties.get(MAIL_TRANSPORT_PROTOCOL).toString();
			String host = properties.get(MAIL_SMTP_HOST).toString();
			String username = properties.get(MAIL_SMTP_USERNAME).toString();
			String password = properties.get(MAIL_SMTP_PASSWORD).toString();
			Transport transport = session.getTransport(protocol);
			transport.connect(host, username, password);
			// 发送邮件
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			log.error(null, e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 发送邮件
	 * 以默认邮箱发送邮件
	 *
	 * @param to         接受邮箱
	 * @param cc         抄送邮箱
	 * @param bcc        密送邮箱
	 * @param subject    邮件主题
	 * @param content    邮件内容
	 * @param attachment 附件文件列表
	 */
	public static void send(Set<String> to, Set<String> cc, Set<String> bcc, String subject, String content, Set<String> attachment) {
		// Mail属性
		Properties properties = new Properties();
		properties.setProperty(MAIL_TRANSPORT_PROTOCOL, PROTOCOL);
		properties.setProperty(MAIL_SMTP_AUTH, AUTH);
		properties.setProperty(MAIL_SMTP_HOST, HOST);
		properties.setProperty(MAIL_SMTP_PORT, PORT);
		properties.setProperty(MAIL_SMTP_USERNAME, USERNAME);
		properties.setProperty(MAIL_SMTP_PASSWORD, AUTH_CODE);
		send(properties, USERNAME, to, cc, bcc, subject, content, attachment);
	}

	/**
	 * 多个to,多个cc,多个bcc
	 */
	public static void send(Set<String> to, Set<String> cc, Set<String> bcc, String subject, String content) {
		send(to, cc, bcc, subject, content, null);
	}

	/**
	 * 多个to,多个cc
	 */
	public static void send(Set<String> to, Set<String> cc, String subject, String content) {
		send(to, cc, null, subject, content, null);
	}

	/**
	 * 一个to,多个cc
	 */
	public static void send(String to, Set<String> cc, String subject, String content) {
		send(Collections.singleton(to), cc, null, subject, content, null);
	}

	/**
	 * 多个to,一个cc
	 */
	public static void send(Set<String> to, String cc, String subject, String content) {
		send(to, Collections.singleton(cc), null, subject, content, null);
	}

	/**
	 * 一个to,一个cc
	 */
	public static void send(String to, String cc, String subject, String content) {
		send(Collections.singleton(to), Collections.singleton(cc), null, subject, content, null);
	}

	/**
	 * 一个to
	 */
	public static void send(String to, String subject, String content) {
		send(Collections.singleton(to), null, null, subject, content, null);
	}

	public static void send(String to, String content) {
		send(to, null, content);
	}

	/**
	 * 添加附件
	 */
	private static void addAttachment(Set<String> filepathSet, Multipart multipart)
			throws MessagingException, UnsupportedEncodingException {
		for (String filepath : filepathSet) {
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			FileDataSource fileDataSource = new FileDataSource(filepath);
			mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
			mimeBodyPart.setFileName(MimeUtility.encodeText(fileDataSource.getName(), "GBK", "B"));
			multipart.addBodyPart(mimeBodyPart);
		}
	}

	/**
	 * 转换邮箱地址
	 */
	private static InternetAddress[] toInternetAddress(Set<String> addressSet) throws Exception {
		Set<InternetAddress> internetAddressSet = new HashSet<>(addressSet.size());
		for (String item : addressSet) {
			internetAddressSet.add(new InternetAddress(item));
		}
		InternetAddress[] addresses = new InternetAddress[addressSet.size()];
		internetAddressSet.toArray(addresses);
		return addresses;
	}

}