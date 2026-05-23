package com.farah.taskmanagement.boardservice.board;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BoardSummaryListener {

    private final BoardSummaryRepository boardSummaryRepository;

    public BoardSummaryListener(BoardSummaryRepository boardSummaryRepository) {
        this.boardSummaryRepository = boardSummaryRepository;
    }

    @RabbitListener(queues = "${board.summary.queue}")
    public void handleBoardSummaryRequested(BoardSummaryRequestedEvent event) {
        String summary = "Board " + event.boardId() + " summary requested by " + event.requestedBy() + ".";
        boardSummaryRepository.save(new BoardSummary(
                event.requestId(),
                event.boardId(),
                event.requestedBy(),
                summary,
                Instant.now()
        ));
    }
}
