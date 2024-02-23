package org.medical.hub.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class SendMailService {

    private static final Logger logger = LoggerFactory.getLogger(SendMailService.class);
    private static final String ENCODING = "utf-8";

    private void properties(JavaMailSenderImpl mailSender) {
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.host", "smtpout.secureserver.net");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.user", "info@nikolaymadoyan.com");
        properties.put("mail.password", "987zqx165165");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.ssl.trust", true);
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
    }

    public JavaMailSender emailSender(MailDto mailDetail) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailDetail.getSmtp());
        mailSender.setPort(mailDetail.getPort());
        mailSender.setUsername(mailDetail.getFromEmail());
        mailSender.setPassword("987zqx165165");

        properties(mailSender);
        return mailSender;
    }

    public boolean sendMail(MailDto mailDetail) {

        JavaMailSender mailSender = emailSender(mailDetail);
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper email = new MimeMessageHelper(mimeMessage, ENCODING);

            email.setTo(mailDetail.getSendTo());
            email.setSubject(mailDetail.getSubject());
            email.setFrom(new InternetAddress(mailDetail.getFromEmail(), getFromName(mailDetail.getFromName())));
            email.setText(mailDetail.getContent(), true);

            mailSender.send(mimeMessage);
            return true;
        } catch (Exception ex) {
            logger.error("Error while sending email.", ex);
            return false;
        }
    }

    private String getFromName(String name) {
        if (name.equals("")) {
            name = "Medical Hub";
        }

        return name;
    }
}
