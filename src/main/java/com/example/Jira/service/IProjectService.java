package com.example.Jira.service;

import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateProjectRequest;

import java.util.List;
import java.util.Map;

public interface IProjectService {

    ProjectDto save(CreateProjectRequest request, User user);

    List<ProjectDto> getAll();

    List<ProjectDto> getProjectsByUser(User user);

    ProjectDto getProjectById(Long projectId);

    List<UserDto> getUsersByProjectId(Long projectId);

    List<UserDto> getUsersByProjectIdAndRole(Long projectId, List<Roles> roles);

    Map<ProjectDto, UserDto> addUserToProject(Long userId, Long projectId);
}
