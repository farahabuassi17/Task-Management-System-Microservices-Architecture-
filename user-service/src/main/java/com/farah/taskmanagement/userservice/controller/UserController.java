package com.farah.taskmanagement.userservice.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hp
 */

import com.farah.taskmanagement.userservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userId) {

        UserDTO user = new UserDTO(
                userId,
                "Farah AbuAssi",
                "farah@example.com"
        );

        return ResponseEntity.ok(user);
    }
}
