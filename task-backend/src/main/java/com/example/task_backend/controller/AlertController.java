package com.example.task_backend.controller;

import com.example.task_backend.entity.Task;
import com.example.task_backend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AlertController {

    private final TaskService taskService;

    @GetMapping("/check/{userId}")
    public ResponseEntity<List<Map<String, Object>>> checkAlerts(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTasksByUser(userId);
        List<Map<String, Object>> alerts = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        for (Task task : tasks) {
            if (task.getDueDate() != null &&
                    !"COMPLETED".equals(task.getStatus()) &&
                    !"EXPIRED".equals(task.getStatus()) &&
                    !task.getIsAlertSent()) {

                long hoursRemaining = java.time.Duration.between(now, task.getDueDate()).toHours();

                if (task.getAlertTime() != null && hoursRemaining <= task.getAlertTime() && hoursRemaining >= 0) {
                    Map<String, Object> alert = new HashMap<>();
                    alert.put("taskId", task.getId());
                    alert.put("title", task.getTitle());
                    alert.put("dueDate", task.getDueDate());
                    alert.put("hoursRemaining", hoursRemaining);
                    alert.put("message", "Task '" + task.getTitle() + "' is due in " + hoursRemaining + " hours!");
                    alerts.add(alert);

                    taskService.markAlertSent(task.getId());
                }
            }
        }

        return ResponseEntity.ok(alerts);
    }
}