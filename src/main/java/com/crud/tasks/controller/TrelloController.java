package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("v1/trello")
public class TrelloController {

    @Autowired
    private TrelloFacade trelloFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/boards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloFacade.fetchTrelloBoards();

//        trelloClient.getTrelloBoards()
        //.stream()
        //.filter(trelloBoardDto -> trelloBoardDto.getId() != null)
        //.filter(trelloBoardDto -> trelloBoardDto.getName() != null)
        //.filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
//                .forEach(trelloBoardDto -> {
//                    System.out.println(trelloBoardDto.getName() + " - " + trelloBoardDto.getId() + "\n This board contains lists: ");
//                    trelloBoardDto.getLists().forEach(trelloList ->
//                            System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed()));
//                });
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cards")
    public CreatedTrelloCardDto createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloFacade.createCard(trelloCardDto);
    }
}