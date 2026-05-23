package com.farah.taskmanagement.boardservice.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/board-summaries")
public class BoardSummaryController {

    private final BoardSummaryRepository boardSummaryRepository;

    public BoardSummaryController(BoardSummaryRepository boardSummaryRepository) {
        this.boardSummaryRepository = boardSummaryRepository;
    }

    @GetMapping
    public List<BoardSummaryResponse> getBoardSummaries() {
        return boardSummaryRepository.findAll()
                .stream()
                .map(BoardSummaryResponse::from)
                .toList();
    }
}
