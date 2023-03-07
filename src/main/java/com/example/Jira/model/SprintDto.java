package com.example.Jira.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SprintDto {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long projectId;
    private String projectName;
}
