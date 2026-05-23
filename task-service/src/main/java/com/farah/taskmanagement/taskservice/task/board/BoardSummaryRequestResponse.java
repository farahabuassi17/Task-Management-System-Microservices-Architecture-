package com.farah.taskmanagement.taskservice.task.board;

import java.time.Instant;

public record BoardSummaryRequestResponse(
        String requestId,
        String boardId,
        String status,
        Instant requestedAt
) {
    public static BoardSummaryRequestResponse accepted(BoardSummaryRequestedEvent event) {
        return new BoardSummaryRequestResponse(
                event.requestId(),
                event.boardId(),
                "ACCEPTED",
                event.requestedAt()
        );
    }
}
