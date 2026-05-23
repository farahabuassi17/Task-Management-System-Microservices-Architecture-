package com.farah.taskmanagement.boardservice.board;

import java.time.Instant;

public record BoardSummaryRequestedEvent(
        String requestId,
        String boardId,
        String requestedBy,
        Instant requestedAt
) {
}
