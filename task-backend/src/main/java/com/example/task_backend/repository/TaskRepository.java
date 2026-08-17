package com.example.task_backend.repository;

import com.example.task_backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    // Find tasks that need alert (due date is approaching and alert not sent)
    @Query("SELECT t FROM Task t WHERE t.userId = :userId " +
            "AND t.dueDate IS NOT NULL " +
            "AND t.isAlertSent = false " +
            "AND t.status NOT IN ('COMPLETED', 'EXPIRED') " +
            "AND t.dueDate <= :alertTime")
    List<Task> findTasksNeedingAlert(@Param("userId") Long userId,
                                     @Param("alertTime") LocalDateTime alertTime);

    // Find all tasks with due date in the past (expired)
    @Query("SELECT t FROM Task t WHERE t.dueDate IS NOT NULL " +
            "AND t.dueDate < :now " +
            "AND t.status NOT IN ('COMPLETED', 'EXPIRED')")
    List<Task> findExpiredTasks(@Param("now") LocalDateTime now);
}