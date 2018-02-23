package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Service
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloBoardDto> boardDto = new ArrayList<>();
        boardDto.add(new TrelloBoardDto("test_id", "test_boardDto", new ArrayList<>()));
        //When
        trelloMapper.mapToBoards(boardDto);
        //Then
        assertEquals(1, trelloMapper.mapToBoards(boardDto).size());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloBoard> board = new ArrayList<>();
        board.add(new TrelloBoard("test_id", "test_board", new ArrayList<>()));
        //When
        trelloMapper.mapToBoardsDto(board);
        //Then
        assertEquals("test_board", trelloMapper.mapToBoardsDto(board).get(0).getName());
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> listDto = new ArrayList<>();
        listDto.add(new TrelloListDto("test_id", "test_listDto", true));
        //When
        trelloMapper.mapToList(listDto);
        //Then
        assertEquals(1, trelloMapper.mapToList(listDto).size());
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> list = new ArrayList<>();
        list.add(new TrelloList("test_id", "test_list", true));
        //When
        trelloMapper.mapToListDto(list);
        //Then
        assertEquals(1, trelloMapper.mapToListDto(list).size());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("test_id", "test_cardDto", "testing", "1");
        //When
        trelloMapper.mapToCard(cardDto);
        //Then
        assertEquals("1", trelloMapper.mapToCard(cardDto).getListId());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard card = new TrelloCard("test_id", "test_card", "testing", "1");
        //When
        trelloMapper.mapToCardDto(card);
        //Then
        assertEquals("test_card", trelloMapper.mapToCardDto(card).getDescription());
    }
}
