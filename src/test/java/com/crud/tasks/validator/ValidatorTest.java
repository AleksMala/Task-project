package com.crud.tasks.validator;

import com.crud.tasks.domain.Trello;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@Service
public class ValidatorTest {

    @Autowired
    private TrelloValidator trelloValidator = new TrelloValidator();

    @Test
    public void testValidateTrelloBoards() {
        //Given
        List<TrelloList> trelloList = Arrays.asList(new TrelloList("1", "trelloList", false));
        List<TrelloBoard> trelloBoards = Arrays.asList(new TrelloBoard("1", "trelloBoard_test", trelloList),
                new TrelloBoard("2", "secondTrelloBoard", trelloList));
        //When
        List<TrelloBoard> getTrelloBoard = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        assertEquals(2, getTrelloBoard.size());
    }

    @Test
    public void testValidateTrelloBoardsWhenDeletingElement() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
            trelloList.add(new TrelloList("1", "trelloList", false));
        List<TrelloBoard> trelloBoards = new ArrayList<>();
                trelloBoards.add(new TrelloBoard("1", "test", trelloList));
                trelloBoards.add(new TrelloBoard("2", "secondTrelloBoard", trelloList));
        //When
        List<TrelloBoard> getTrelloBoard = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        assertEquals(1, getTrelloBoard.size());
    }
}
