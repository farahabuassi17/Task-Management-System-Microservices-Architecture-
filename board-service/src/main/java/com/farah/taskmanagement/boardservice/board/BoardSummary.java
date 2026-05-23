package com.farah.taskmanagement.boardservice.board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "board_summaries")
public class BoardSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String requestId;

    @Column(nullable = false)
    private String boardId;

    @Column(nullable = false)
    private String requestedBy;

    @Column(nullable = false, length = 1000)
    private String summary;

    @Column(nullable = false)
    private Instant createdAt;

    protected BoardSummary() {
    }

    public BoardSummary(String requestId, String boardId, String requestedBy, String summary, Instant createdAt) {
        this.requestId = requestId;
        this.boardId = boardId;
        this.requestedBy = requestedBy;
        this.summary = summary;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public String getSummary() {
        return summary;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
