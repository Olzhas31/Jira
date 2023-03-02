package com.example.Jira.model.requests;

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
public class CreateSprintRequest {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long projectId;
}
