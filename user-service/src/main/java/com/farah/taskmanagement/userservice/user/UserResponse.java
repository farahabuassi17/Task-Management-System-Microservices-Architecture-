package com.farah.taskmanagement.userservice.user;

public record UserResponse(
        String id,
        String fullName,
        String email,
        String role,
        boolean active
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        );
    }
}
