package com.example.Jira.service.impl;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.Sprint;
import com.example.Jira.entity.Task;
import com.example.Jira.entity.TaskHistory;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.mapper.SprintMapper;
import com.example.Jira.model.SprintDto;
import com.example.Jira.model.requests.CreateSprintRequest;
import com.example.Jira.repository.ProjectRepository;
import com.example.Jira.repository.SprintRepository;
import com.example.Jira.repository.TaskHistoryRepository;
import com.example.Jira.repository.TaskRepository;
import com.example.Jira.service.ISprintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.Jira.configuration.Constants.*;

@Service
@AllArgsConstructor
public class SprintServiceImpl implements ISprintService {

    private final SprintRepository repository;
    private final SprintMapper mapper;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TaskHistoryRepository taskHistoryRepository;

    @Override
    public SprintDto save(CreateSprintRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND + request.getProjectId()));
        Sprint sprint = mapper.toEntity(request, project);
        sprint = repository.save(sprint);
        return mapper.toDto(sprint);
    }

    @Override
    public List<SprintDto> getByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND + projectId));
        return repository.findAllByProject(project)
                .stream().map(mapper::toDto)
                .toList();
    }

    @Override
    public SprintDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(SPRINT_NOT_FOUND + id));
    }

    @Override
    public SprintDto update(Long id, String name, LocalDate startDate, LocalDate endDate) {
        Sprint sprint = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SPRINT_NOT_FOUND + id));

        sprint.setName(name);
        sprint.setStartDate(startDate);
        sprint.setEndDate(endDate);

        sprint = repository.save(sprint);
        return mapper.toDto(sprint);
    }

    @Transactional
    @Override
    public Long deleteById(Long sprintId) {
        Sprint sprint = repository.findById(sprintId)
                .orElseThrow(() -> new EntityNotFoundException(SPRINT_NOT_FOUND + sprintId));

        repository.deleteTasksInSprint(sprintId);
        repository.deleteById(sprintId);
        return sprint.getProject().getId();
    }

    @Override
    public void addTaskToSprint(Long taskId, Long sprintId) {
        Sprint sprint = repository.findById(sprintId)
                .orElseThrow(() -> new EntityNotFoundException(SPRINT_NOT_FOUND + sprintId));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskId));
        if (!task.getSprints().contains(sprint)) {
            task.getSprints().add(sprint);
            taskRepository.save(task);

            taskHistoryRepository.save(
                    TaskHistory.builder()
                            .transition(UPDATE_TASK_HISTORY + task)
                            .createdTime(LocalDateTime.now())
                            .task(task)
                            .build()
            );
        }
    }

    @Override
    public List<SprintDto> getByTaskId(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskId));
        return task.getSprints()
                .stream().map(mapper::toDto)
                .toList();
    }

    @Override
    public void deleteTaskInSprint(Long taskId, Long sprintId) {
        Sprint sprint = repository.findById(sprintId)
                .orElseThrow(() -> new EntityNotFoundException(SPRINT_NOT_FOUND + sprintId));
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskId));
        if (task.getSprints().contains(sprint)) {
            task.getSprints().remove(sprint);
            task = taskRepository.save(task);

            taskHistoryRepository.save(
                    TaskHistory.builder()
                            .transition(UPDATE_TASK_HISTORY + task)
                            .createdTime(LocalDateTime.now())
                            .task(task)
                            .build()
            );
        }
    }
}
