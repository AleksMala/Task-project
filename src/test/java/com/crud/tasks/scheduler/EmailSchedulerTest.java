package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class EmailSchedulerTest {

    @MockBean
    private EmailScheduler emailScheduler;
    @MockBean
    private SimpleEmailService simpleEmailService;
    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private AdminConfig adminConfig;

    @Test
    public void testEmailScheduler() {
        //Given
        Mail mail = new Mail("malaleksandra2@gmail.com", "Test", "Test message");

        when(adminConfig.getAdminMail()).thenReturn("malaleksandra2@gmail.com");
        when(taskRepository.count()).thenReturn(anyLong());
        simpleEmailService.send(mail);

        //When
        emailScheduler.sendInformationEmail();

        //Then
        verify(emailScheduler, times(1)).sendInformationEmail();
    }

    @Test
    public void testEmailSchedulerNotSent() {
        //Given
        Mail mail = new Mail("malaleksandra2@gmail.com", "Test", "Test message");

        //when(adminConfig.getAdminMail()).thenReturn("malaleksandra2@gmail.com");
        when(taskRepository.count()).thenReturn(anyLong());
        simpleEmailService.send(mail);

        //When
        emailScheduler.sendInformationEmail();
        //Then

    }
}
