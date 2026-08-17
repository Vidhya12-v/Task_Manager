package com.example.task_backend.service;

import com.example.task_backend.entity.Task;
import com.example.task_backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public Task createTask(Task task) {
        log.info("Creating task: {}", task.getTitle());
        if (task.getStatus() == null) {
            task.setStatus("PENDING");
        }
        task.setIsAlertSent(false);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        log.info("Getting all tasks");
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        log.info("Getting task by id: {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByUser(Long userId) {
        log.info("Getting tasks for user: {}", userId);
        List<Task> tasks = taskRepository.findByUserId(userId);

        // Check and update expired tasks
        for (Task task : tasks) {
            checkAndUpdateTaskStatus(task);
        }

        return tasks;
    }

    @Transactional
    public Task updateTask(Long id, Task taskDetails) {
        log.info("Updating task: {}", id);
        Task existingTask = getTaskById(id);

        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());

        if ("COMPLETED".equals(taskDetails.getStatus())) {
            existingTask.setStatus("COMPLETED");
        } else {
            existingTask.setStatus(taskDetails.getStatus());
        }

        existingTask.setDueDate(taskDetails.getDueDate());
        existingTask.setAlertTime(taskDetails.getAlertTime());

        return taskRepository.save(existingTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        log.info("Deleting task: {}", id);
        taskRepository.deleteById(id);
    }

    @Transactional
    public void checkAndUpdateTaskStatus(Task task) {
        if ("COMPLETED".equals(task.getStatus()) || "EXPIRED".equals(task.getStatus())) {
            return;
        }

        if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDateTime.now())) {
            task.setStatus("EXPIRED");
            taskRepository.save(task);
            log.info("Task {} marked as EXPIRED", task.getId());
        }
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksNeedingAlert(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return taskRepository.findTasksNeedingAlert(userId, now);
    }

    @Transactional
    public void markAlertSent(Long taskId) {
        Task task = getTaskById(taskId);
        task.setIsAlertSent(true);
        taskRepository.save(task);
    }
}