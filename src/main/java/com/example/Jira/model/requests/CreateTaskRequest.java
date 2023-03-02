package com.example.Jira.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {

    private String description;
    private String priority;
    private String title;

    private Long assigneeId;
    private Long developerId;
    private Long expertId;
    private Long reviewerId;
    private Long projectId;

}
