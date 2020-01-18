package com.techleads.app.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.techleads.app.model.Mail;

@Service
public class MailServiceImpl implements MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public boolean sendMail(Mail mail) throws Exception {
		boolean isSent = false;
		try {
			//1. create Message    
			MimeMessage message=javaMailSender.createMimeMessage(); 
			//true=>allocate memory for attachment 
			MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message, mail.getAttachments()!=null,"UTF-8"); 
			if(mail.getMailTo()!=null) {
				mimeMessageHelper.setTo(mail.getMailTo());//To is mandatory
			}
			if(mail.getMailFrom()!=null) {
				mimeMessageHelper.setFrom(mail.getMailFrom()); //From is mandatory 
			}
			
			if(mail.getMailCc()!=null) {
				mimeMessageHelper.setCc(mail.getMailCc()); //Sending CC is optional 
			}
			if(mail.getMailBcc()!=null) {
				mimeMessageHelper.setBcc(mail.getMailBcc());//Sending Bcc is optional 
			}
			if(mail.getMailSubject()!=null) {
			mimeMessageHelper.setSubject(mail.getMailSubject());//Sending subject optional
			}
		
			if(mail.getMailContent()!=null) {
				mimeMessageHelper.setText(mail.getMailContent(),true); //true is mandatory to enable html content
			}
			if (mail.getAttachments() != null) {
				for (Object attachment : mail.getAttachments()) {
					File file = ((ClassPathResource) attachment).getFile();
					file.setExecutable(true);
					file.setReadable(true);
					file.setWritable(true);
					mimeMessageHelper.addAttachment(file.getName(), file);
				}
			}
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
			isSent=true;
			logger.info("mail has been sent");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSent;
	}

}
