package com.farah.taskmanagement.taskservice.task;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserServiceClient userServiceClient;

    public TaskService(TaskRepository taskRepository, UserServiceClient userServiceClient) {
        this.taskRepository = taskRepository;
        this.userServiceClient = userServiceClient;
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        UserSummary assignedUser = userServiceClient.getActiveUser(request.assignedUserId());

        Task task = new Task(
                request.title(),
                request.description(),
                "OPEN",
                assignedUser.id(),
                assignedUser.fullName(),
                assignedUser.email(),
                Instant.now()
        );

        return TaskResponse.from(taskRepository.save(task));
    }

    public TaskResponse getTask(Long id) {
        return taskRepository.findById(id)
                .map(TaskResponse::from)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
