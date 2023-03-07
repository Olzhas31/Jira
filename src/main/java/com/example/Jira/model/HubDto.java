package com.example.Jira.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HubDto {

    private Long id;
    private String content;
    private LocalDateTime createdTime;
    private String name;
    private LocalDateTime updatedTime;
    private Long creatorId;
    private Long updaterId;
    private Long projectId;
    private String updaterName;
    private String projectName;
    private String creatorName;
}
