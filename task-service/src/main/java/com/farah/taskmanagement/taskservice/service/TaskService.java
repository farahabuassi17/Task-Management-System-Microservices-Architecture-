package com.farah.taskmanagement.taskservice.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hp
 */
import com.farah.taskmanagement.taskservice.dto.UserDTO;
import com.farah.taskmanagement.taskservice.entity.Task;
import com.farah.taskmanagement.taskservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Task createIncomingTask(Task task) {

        String url = "http://localhost:8082/api/users/" + task.getAssigneeId();

        UserDTO user = restTemplate.getForObject(url, UserDTO.class);

        if (user != null) {
            return taskRepository.save(task);
        }

        throw new RuntimeException("User not found");
    }
}