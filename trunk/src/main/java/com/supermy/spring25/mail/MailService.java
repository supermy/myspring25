package com.supermy.spring25.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * 邮件服务
 * 
 * 
 * 
 * 
 */

public class MailService {

	protected final Log logger = LogFactory.getLog(getClass());

	private JavaMailSender javaMailSender;

	private Configuration freemarkerConfiguration;

	private TaskExecutor taskExecutor;

	private String encoding = "utf-8";

	private static String from = null;// ConfigUtil.getValue("admin_mail");

	private static String fromName = null;// ConfigUtil.getValue("admin_mail_name");

	private static InternetAddress fromAddress;

	private InternetAddress getAddress() {

		if (fromAddress == null) {

			try {

				fromAddress = new InternetAddress(from, fromName);

			} catch (Exception e) {
			}

		}

		return fromAddress;

	}

	/**
	 * 
	 * 发送用户注册邮件
	 * 
	 * @param user
	 */

	public void sendRegistMail(Map<String, String> user) {

		Map<String, Object> parseMap = new HashMap<String, Object>();

		parseMap.put("user", user);

		setAsyncTemplateMail(user.get("email"), "欢迎来到geelou专业个人网站", encoding,
				"registerok.html", parseMap);

	}

	/**
	 * 
	 * 发送文本邮件
	 */

	public void sendSimpleMail(String to, String subject, String text) {

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setFrom(from);

		msg.setTo(to);

		msg.setSubject(subject);

		msg.setText(text);

		javaMailSender.send(msg);

	}

	/**
	 * 
	 * 异步发送模版邮件
	 * 
	 * @param to
	 * 
	 * @param subject
	 * 
	 * @param encoding
	 * 
	 * @param template
	 * 
	 * @param parse
	 * 
	 * @throws Exception
	 */

	public void setAsyncTemplateMail(final String to, final String subject,
			final String encoding, final String template,
			final Map<String, Object> parse) {

		taskExecutor.execute(new Runnable() {

			public void run() {

				try {

					setTemplateMail(to, subject, encoding, template, parse);

				} catch (Exception e) {

					logger.error("邮件发送失败，失败提示：" + e.getMessage());

				}

			}

		}

		);

	}

	/**
	 * 
	 * 发送模版邮件
	 * 
	 * @param to
	 * 
	 * @param subject
	 * 
	 * @param encoding
	 * 
	 * @param template
	 * 
	 * @param parse
	 * 
	 * @throws Exception
	 */

	public void setTemplateMail(String to, String subject, String encoding,
			String template, Map<String, Object> parse) throws Exception {

		Template templateS = freemarkerConfiguration.getTemplate(template,
				encoding);

		String htmlCnt = FreeMarkerTemplateUtils.processTemplateIntoString(
				templateS, parse);

		sendHTMLMail(to, subject, htmlCnt, encoding);

	}

	/**
	 * 
	 * 发送html格式邮件
	 * 
	 * @param to
	 * 
	 * @param subject
	 * 
	 * @param htmlCnt
	 * 
	 * @param encoding
	 * 
	 * @throws MessagingException
	 * @throws javax.mail.MessagingException
	 */

	public void sendHTMLMail(String to, String subject, StringBuffer htmlCnt,
			String encoding) throws MessagingException,
			javax.mail.MessagingException {

		sendHTMLMail(to, subject, htmlCnt.toString(), encoding);

	}

	/**
	 * 
	 * 发送html格式邮件
	 * 
	 * @param to
	 * 
	 * @param subject
	 * 
	 * @param htmlCnt
	 * 
	 * @param encoding
	 * 
	 * @throws MessagingException
	 * @throws javax.mail.MessagingException
	 */

	public void sendHTMLMail(String to, String subject, String htmlCnt,
			String encoding) throws MessagingException,
			javax.mail.MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
				mimeMessage, false, encoding);

		mimeMessageHelper.setFrom(getAddress());

		mimeMessageHelper.setTo(to);

		mimeMessageHelper.setBcc(getAddress());

		mimeMessageHelper.setSubject(subject);

		mimeMessageHelper.setText(htmlCnt, true);

		javaMailSender.send(mimeMessage);

	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {

		this.javaMailSender = javaMailSender;

	}

	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {

		this.freemarkerConfiguration = freemarkerConfiguration;

	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {

		this.taskExecutor = taskExecutor;

	}

}