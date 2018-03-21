package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail, final EmailTemplate emailTemplate) {
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail, emailTemplate));
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Faild to process email sending: ", e.getMessage(), e);
        }
    }

    private String getTemplate(final String message, final EmailTemplate emailTemplate) {
        switch (emailTemplate) {
            case SCHEDULER_TEMPLATE:
                return mailCreatorService.buildEmailScheduler(message);
            case TRELLO_CARD_TEMPLATE:
                return mailCreatorService.buildTrelloCardEmail(message);
            default:
                return message;
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail, final EmailTemplate emailTemplate) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(getTemplate(mail.getMessage(), emailTemplate), true);
        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        Optional.ofNullable(mail.getToCC()).ifPresent(mailMessage::setCc);

        return mailMessage;
    }

}
