package com.crud.tasks.trello.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService = new TrelloService();
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService simpleEmailService;

    @Test
    public void testCreatedTrelloCardDto() {
        //Given
        Mail mail = new Mail("malaleksandra2@gmail.com", "Test", "Test message");
        TrelloCardDto trelloCardDto = new TrelloCardDto("trello_card", "card_test", "trello", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "service_test", "https://test.com");

        when(adminConfig.getAdminMail()).thenReturn("malaleksandra2@gmail.com");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto newCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals("1", newCard.getId());
        assertEquals("service_test", newCard.getName());
        assertEquals("https://test.com", newCard.getShortUrl());
    }

    @Test
    public void testFailedToCreateTrelloCardDto() {
        //Given
        when(trelloClient.createNewCard(ArgumentMatchers.any())).thenReturn(null);
        //When
        //Then
        assertEquals(null, trelloService.createTrelloCard(null));
    }
}
