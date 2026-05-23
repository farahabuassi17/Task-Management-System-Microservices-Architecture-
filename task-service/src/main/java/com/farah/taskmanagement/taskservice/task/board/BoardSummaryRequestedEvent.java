package com.farah.taskmanagement.taskservice.task.board;

import java.time.Instant;
import java.util.UUID;

public record BoardSummaryRequestedEvent(
        String requestId,
        String boardId,
        String requestedBy,
        Instant requestedAt
) {
    public static BoardSummaryRequestedEvent create(String boardId, String requestedBy) {
        return new BoardSummaryRequestedEvent(
                UUID.randomUUID().toString(),
                boardId,
                requestedBy,
                Instant.now()
        );
    }
}
