package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Service
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper = new TrelloMapper();


    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloListDto> trelloListDtoToMap = Arrays.asList(new TrelloListDto("list_id", "list_name", true));
        List<TrelloBoardDto> trelloBoardDtoToMap = Arrays.asList(new TrelloBoardDto("test_id", "test_boardDto", trelloListDtoToMap));
        //When
        List<TrelloBoard> acctualTrelloBoards = trelloMapper.mapToBoards(trelloBoardDtoToMap);
        //Then
        assertEquals(1, acctualTrelloBoards.size());
        assertEquals("test_id", acctualTrelloBoards.get(0).getId());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloList> trelloListDtoExpected = Arrays.asList(new TrelloList("list_id", "list_name", true));
        List<TrelloBoard> trelloBoardExpected = Arrays.asList(new TrelloBoard("test_id", "test_boardDto", trelloListDtoExpected));
        //When
        List<TrelloBoardDto> acctualTrelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoardExpected);
        //Then
        assertEquals("test_boardDto", acctualTrelloBoardsDto.get(0).getName());
        assertEquals(1, acctualTrelloBoardsDto.size());
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDtoToMap = Arrays.asList(new TrelloListDto("list_id", "list_name", true));
        List<TrelloList> mappedtrelloListDto = Arrays.asList(new TrelloList("list_id", "list_name", true));
        //When
        List<TrelloList> acctualTrelloList = trelloMapper.mapToList(trelloListDtoToMap);
        //Then
        assertEquals(mappedtrelloListDto, acctualTrelloList);
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloListDtoToMap = Arrays.asList(new TrelloList("list_id", "list_name", true));
        List<TrelloListDto> mappedtrelloListDto = Arrays.asList(new TrelloListDto("list_id", "list_name", true));
        //When
        List<TrelloListDto> acctualTrelloListDto = trelloMapper.mapToListDto(trelloListDtoToMap);
        //Then
        assertEquals(mappedtrelloListDto, acctualTrelloListDto);
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto cardDtoToMap = new TrelloCardDto("test_id", "test_cardDto", "testing", "1");
        TrelloCard mappedCard = new TrelloCard("test_id", "test_cardDto", "testing", "1");
        //When
        TrelloCard acctualTrelloCard = trelloMapper.mapToCard(cardDtoToMap);
        //Then
        assertEquals(mappedCard, acctualTrelloCard);
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard cardToMap = new TrelloCard("test_idDto", "test_card", "testing", "1");
        TrelloCardDto mappedCardDto = new TrelloCardDto("test_idDto", "test_card", "testing", "1");
        //When
        TrelloCardDto acctualTrelloCardDto = trelloMapper.mapToCardDto(cardToMap);
        //Then
        assertEquals(mappedCardDto, acctualTrelloCardDto);
    }
}
