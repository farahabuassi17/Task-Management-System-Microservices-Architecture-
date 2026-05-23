package com.farah.taskmanagement.boardservice.board;

import java.time.Instant;

public record BoardSummaryResponse(
        Long id,
        String requestId,
        String boardId,
        String requestedBy,
        String summary,
        Instant createdAt
) {
    public static BoardSummaryResponse from(BoardSummary boardSummary) {
        return new BoardSummaryResponse(
                boardSummary.getId(),
                boardSummary.getRequestId(),
                boardSummary.getBoardId(),
                boardSummary.getRequestedBy(),
                boardSummary.getSummary(),
                boardSummary.getCreatedAt()
        );
    }
}
