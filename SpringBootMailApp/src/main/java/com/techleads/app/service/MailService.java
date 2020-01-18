package com.techleads.app.service;

import com.techleads.app.model.Mail;

public interface MailService {
	boolean sendMail(Mail mail) throws Exception;

}
