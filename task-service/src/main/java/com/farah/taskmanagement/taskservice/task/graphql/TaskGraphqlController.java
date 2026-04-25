package com.farah.taskmanagement.taskservice.task.graphql;

import com.farah.taskmanagement.taskservice.task.CreateTaskRequest;
import com.farah.taskmanagement.taskservice.task.TaskResponse;
import com.farah.taskmanagement.taskservice.task.TaskService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TaskGraphqlController {

    private final TaskService taskService;

    public TaskGraphqlController(TaskService taskService) {
        this.taskService = taskService;
    }

    @QueryMapping
    public TaskResponse task(@Argument Long id) {
        return taskService.getTask(id);
    }

    @MutationMapping
    public TaskResponse createTask(@Argument CreateTaskInput input) {
        return taskService.createTask(new CreateTaskRequest(
                input.title(),
                input.description(),
                input.assignedUserId()
        ));
    }
}
