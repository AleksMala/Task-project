package com.crud.tasks.trello.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.EmailTemplate;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private EmailTemplate emailTemplate;

    @Test
    public void testCreatedTrelloCardDto() {
        //Given
        Mail mail = new Mail(
                "malaleksandra2@gmail.com",
                "Tasks: New Trello Card",
                "New Card service_test has been created on your Trello account");
        TrelloCardDto trelloCardDto = new TrelloCardDto("trello_card", "card_test", "trello", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "service_test", "https://test.com");

        when(adminConfig.getAdminMail()).thenReturn("malaleksandra2@gmail.com");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto newCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        verify(simpleEmailService, times(1)).send(argThat(new MailMatcher(mail)), emailTemplate);
        assertEquals("1", newCard.getId());
        assertEquals("service_test", newCard.getName());
        assertEquals("https://test.com", newCard.getShortUrl());
    }

    @Test
    public void testFailedToCreateTrelloCardDto() {
        //Given
        Mail mail = new Mail("malaleksandra2@gmail.com", "Test", "Test message");
        when(trelloClient.createNewCard(ArgumentMatchers.any())).thenReturn(null);
        //When&Then
        verify(simpleEmailService, times(0)).send(mail, emailTemplate);
        assertEquals(null, trelloService.createTrelloCard(null));
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
