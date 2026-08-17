package com.example.task_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String status; // PENDING, IN_PROGRESS, COMPLETED, EXPIRED

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "due_date")
    private LocalDateTime dueDate;  // NEW - Task deadline

    @Column(name = "alert_time")
    private Integer alertTime;  // NEW - Hours before due date to alert (24, 12, 6, 1)

    @Column(name = "is_alert_sent")
    private Boolean isAlertSent = false;  // NEW - Track if alert was sent

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}