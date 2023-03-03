package com.example.Jira.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_logs")
@Entity
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "payload", nullable = false, length = 1000)
    private String payload;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}