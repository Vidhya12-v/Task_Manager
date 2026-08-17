package com.example.task_backend.controller;

import com.example.task_backend.entity.User;
import com.example.task_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            response.put("success", false);
            response.put("message", "User not found!");
            return ResponseEntity.badRequest().body(response);
        }

        // Simple password check (no encryption for now)
        if (!password.equals(user.getPassword())) {
            response.put("success", false);
            response.put("message", "Wrong password!");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("success", true);
        response.put("message", "Login successful!");
        response.put("userId", user.getId());
        response.put("username", user.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        if (userRepository.existsByUsername(user.getUsername())) {
            response.put("success", false);
            response.put("message", "Username already exists!");
            return ResponseEntity.badRequest().body(response);
        }

        User savedUser = userRepository.save(user);

        response.put("success", true);
        response.put("message", "User registered successfully!");
        response.put("userId", savedUser.getId());
        response.put("username", savedUser.getUsername());

        return ResponseEntity.ok(response);
    }
}