package com.farah.taskmanagement.taskservice.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author hp
 */
import com.farah.taskmanagement.taskservice.entity.Task;
import com.farah.taskmanagement.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;    // استيراد ضروري
import org.springframework.http.ResponseEntity; // استيراد ضروري
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            // استدعاء الخدمة للتحقق من المستخدم وحفظ المهمة
            Task createdTask = taskService.createIncomingTask(task);
            
            // في حال النجاح، نعيد حالة 201 (Created) مع البيانات
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (RuntimeException e) {
            // في حال الفشل (مثلاً المستخدم غير موجود)، نعيد حالة 400 (Bad Request)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}