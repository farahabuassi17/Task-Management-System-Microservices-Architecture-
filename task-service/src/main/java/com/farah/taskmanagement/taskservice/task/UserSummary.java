package com.farah.taskmanagement.taskservice.task;

public record UserSummary(
        String id,
        String fullName,
        String email,
        String role,
        boolean active
) {
}
