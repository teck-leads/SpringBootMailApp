package com.techleads.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.techleads.app.model.Mail;
import com.techleads.app.service.MailService;

@Component
public class EmailRunner implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(EmailRunner.class);
	@Autowired

	private MailService mailService;

	@Override
	public void run(String... args) throws Exception {
		Mail mail = new Mail();

		//mail.setMailTo(new String[] { "gmail id " });
		//mail.setMailCc(new String[] { "gmail id" });
		mail.setMailBcc(new String[] { "gmail id","gmail" });
		mail.setMailSubject("Spring Boot Email API version 10");
		mail.setMailContent("<h1>This is a Spring Boot version 10 </h1>");

		boolean isMailSent = mailService.sendMail(mail);
		logger.info("Mail Sent flag "+ isMailSent);

	}

}
