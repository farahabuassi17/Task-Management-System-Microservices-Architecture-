package com.farah.taskmanagement.taskservice.task.board;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
public class BoardSummaryController {

    private final BoardSummaryRequestPublisher boardSummaryRequestPublisher;

    public BoardSummaryController(BoardSummaryRequestPublisher boardSummaryRequestPublisher) {
        this.boardSummaryRequestPublisher = boardSummaryRequestPublisher;
    }

    @PostMapping("/{boardId}/summary-requests")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BoardSummaryRequestResponse requestBoardSummary(
            @PathVariable String boardId,
            @RequestBody BoardSummaryRequest request
    ) {
        BoardSummaryRequestedEvent event = BoardSummaryRequestedEvent.create(boardId, request.requestedBy());
        boardSummaryRequestPublisher.publish(event);
        return BoardSummaryRequestResponse.accepted(event);
    }
}
