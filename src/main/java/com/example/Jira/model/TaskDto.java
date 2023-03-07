package com.example.Jira.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;
    private LocalDateTime createdTime;
    private String description;
    private LocalDate dueDate;
    private String name;
    private String priority;
    private LocalDateTime resolvedTime;
    private LocalDate startDate;
    private String title;
    private LocalDateTime updatedTime;
    private Long acceptorId;
    private String acceptorName;
    private Long assigneeId;
    private String assigneeName;
    private Long developerId;
    private String developerName;
    private Long expertId;
    private String expertName;
    private Long reviewerId;
    private String reviewerName;
    private String projectName;
    private Long projectId;
    private String status;

    private String hubName;
    private Long hubId;
}
