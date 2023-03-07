package com.example.Jira.service;

import com.example.Jira.entity.User;
import com.example.Jira.model.DashboardResponse;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.TaskDto;
import com.example.Jira.model.requests.CreateTaskRequest;

import java.time.LocalDate;
import java.util.List;

public interface ITaskService {

    TaskDto save(CreateTaskRequest request, User user);

    List<TaskDto> getAllByUserAndProjectIdAndStatusesAtActualSprint(User user, Long projectId, List<String> statuses);

    TaskDto getById(Long id);

    List<DashboardResponse> getBacklogTasks(List<ProjectDto> projects);

    List<DashboardResponse> getDashboardTasks(User user, List<ProjectDto> projects, List<String> taskStatuses);

    List<TaskDto> getBacklogTasksByProjectId(Long projectId);

    void update(Long taskId, String title, String description, String priority, String status, Long assigneeId, Long expertId, Long developerId, Long reviewerId, LocalDate dueDate);

    List<TaskDto> getTasksByHubId(Long hubId);

    List<TaskDto> getTasksBySprintId(Long id);
}
