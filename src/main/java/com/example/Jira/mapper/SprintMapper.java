package com.example.Jira.mapper;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.Sprint;
import com.example.Jira.model.SprintDto;
import com.example.Jira.model.requests.CreateSprintRequest;
import org.springframework.stereotype.Service;

@Service
public class SprintMapper {

    public Sprint toEntity(CreateSprintRequest request, Project project) {
        return Sprint.builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .project(project)
                .build();
    }

    public SprintDto toDto(Sprint sprint) {
        return SprintDto.builder()
                .id(sprint.getId())
                .endDate(sprint.getEndDate())
                .name(sprint.getName())
                .startDate(sprint.getStartDate())
                .projectId(sprint.getProject().getId())
                .projectName(sprint.getProject().getName())
                .build();
    }
}
