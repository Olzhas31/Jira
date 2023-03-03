package com.example.Jira.mapper;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.requests.CreateProjectRequest;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectMapper {

    public Project toEntity(CreateProjectRequest request, User pm) {
        return Project.builder()
                .description(request.getDescription())
                .name(request.getName())
                .users(Collections.singletonList(pm))
                .build();
    }

    public ProjectDto toDto(Project project) {
        List<User> onlyPm = project.getUsers().stream()
                .filter(user -> user.getRole().equals(Roles.PROJECT_MANAGER.name()))
                .toList();
        String name = onlyPm.get(0).getUserDetail().getName() == null ?
                " " : onlyPm.get(0).getUserDetail().getName();
        String surname = onlyPm.get(0).getUserDetail().getSurname() == null ?
                " " : onlyPm.get(0).getUserDetail().getSurname();

        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .projectManagerId(onlyPm.get(0).getId())
                .projectManagerName(name + ' ' + surname)
                .goal(project.getGoal())
                .responsibility(project.getResponsibility())
                .build();
    }
}
