package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.EmailTemplate;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class EmailSchedulerTest {

    @InjectMocks
    private EmailScheduler emailScheduler;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private EmailTemplate emailTemplate;

    @Test
    public void testEmailSchedulerMessageWithTasks() {
        //Given
        Mail expectedMail = new Mail("malaleksandra2@gmail.com",
                "Tasks: Once a day email",
                "Currently in database you got: 2 tasks");

        when(adminConfig.getAdminMail()).thenReturn("malaleksandra2@gmail.com");
        when(taskRepository.count()).thenReturn(2L);

        //When
        emailScheduler.sendInformationEmail();

        //Then
        verify(simpleEmailService, times(1)).send(argThat(new MailMatcher(expectedMail)), emailTemplate);
    }

    @Test
    public void testEmailSchedulerMessageWthOneTask() {
        //Given
        Mail expectedMail = new Mail("malaleksandra2@gmail.com",
                "Tasks: Once a day email",
                "Currently in database you got: 1 task");

        when(adminConfig.getAdminMail()).thenReturn("malaleksandra2@gmail.com");
        when(taskRepository.count()).thenReturn(1L);

        //When
        emailScheduler.sendInformationEmail();

        //Then
        verify(simpleEmailService, times(1)).send(argThat(new MailMatcher(expectedMail)), emailTemplate);
    }

    private class MailMatcher implements ArgumentMatcher<Mail> {

        Mail expected;

        MailMatcher(Mail expected) {
            this.expected = expected;
        }

        @Override
        public boolean matches(Mail actual) {
            return expected.getMessage().equals(actual.getMessage());
        }
    }
}
