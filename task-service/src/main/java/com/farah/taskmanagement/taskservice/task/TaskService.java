package com.farah.taskmanagement.taskservice.task;

import com.farah.taskmanagement.taskservice.task.events.TaskEventPublisher;
import com.farah.taskmanagement.taskservice.task.events.TaskUpdatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserServiceClient userServiceClient;
    private final TaskEventPublisher taskEventPublisher;

    public TaskService(
            TaskRepository taskRepository,
            UserServiceClient userServiceClient,
            TaskEventPublisher taskEventPublisher
    ) {
        this.taskRepository = taskRepository;
        this.userServiceClient = userServiceClient;
        this.taskEventPublisher = taskEventPublisher;
    }

    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        UserSummary assignedUser = userServiceClient.getActiveUser(request.assignedUserId());

        Task task = new Task(
                request.title(),
                request.description(),
                "OPEN",
                defaultIfBlank(request.priority(), "MEDIUM"),
                defaultIfBlank(request.boardId(), "B-001"),
                assignedUser.id(),
                assignedUser.fullName(),
                assignedUser.email(),
                Instant.now()
        );

        Task savedTask = taskRepository.save(task);
        taskEventPublisher.publish(TaskUpdatedEvent.statusChanged(
                savedTask.getId(),
                savedTask.getTitle(),
                null,
                savedTask.getStatus(),
                savedTask.getAssignedUserEmail()
        ));
        return TaskResponse.from(savedTask);
    }

    public TaskResponse getTask(Long id) {
        return taskRepository.findById(id)
                .map(TaskResponse::from)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Transactional
    public TaskResponse updateTaskStatus(Long id, UpdateTaskStatusRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        String oldStatus = task.getStatus();
        String newStatus = defaultIfBlank(request.status(), oldStatus);

        task.changeStatus(newStatus);
        Task savedTask = taskRepository.save(task);
        taskEventPublisher.publish(TaskUpdatedEvent.statusChanged(
                savedTask.getId(),
                savedTask.getTitle(),
                oldStatus,
                savedTask.getStatus(),
                savedTask.getAssignedUserEmail()
        ));

        return TaskResponse.from(savedTask);
    }

    private String defaultIfBlank(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}
