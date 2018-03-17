package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private static final String SUBJECT = "Tasks: Once a day email";

    @Autowired
    private SimpleEmailService simpleEmailService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private MailCreatorService mailCreatorService;

    private MimeMessagePreparator messagePreparator (final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildEmailScheduler(mail.getMessage()), true);
        };
    }

    @Scheduled(cron = "0 0/30 8-10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String name;
        if (size == 1) {
            name = " task";
        } else {
            name = " tasks";
        }
        simpleEmailService.send(
                new Mail(
                (adminConfig.getAdminMail()),
                        SUBJECT,
                        "Currently in database you got: " + size + name));
    }

}

