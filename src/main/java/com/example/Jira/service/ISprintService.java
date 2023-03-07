package com.example.Jira.service;

import com.example.Jira.model.SprintDto;
import com.example.Jira.model.requests.CreateSprintRequest;

import java.time.LocalDate;
import java.util.List;

public interface ISprintService {
    SprintDto save(CreateSprintRequest request);

    List<SprintDto> getByProjectId(Long projectId);

    SprintDto getById(Long id);

    void update(Long id, String name, LocalDate startDate, LocalDate endDate);

    Long deleteById(Long sprintId);

    void addTaskToSprint(Long taskId, Long sprintId);

    List<SprintDto> getByTaskId(Long taskId);

    void deleteTaskInSprint(Long taskId, Long sprintId);
}
