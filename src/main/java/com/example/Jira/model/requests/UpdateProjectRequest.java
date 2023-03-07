package com.example.Jira.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectRequest {

    private Long id;
    private String name;
    private String description;
    private String goal;
    private String responsibility;
}
