package com.example.Jira.service.impl;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.Sprint;
import com.example.Jira.entity.Task;
import com.example.Jira.entity.User;
import com.example.Jira.entity.states.TaskStatus;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.mapper.TaskMapper;
import com.example.Jira.model.TaskDto;
import com.example.Jira.model.requests.CreateTaskRequest;
import com.example.Jira.repository.ProjectRepository;
import com.example.Jira.repository.SprintRepository;
import com.example.Jira.repository.TaskRepository;
import com.example.Jira.repository.UserRepository;
import com.example.Jira.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.Jira.configuration.Constants.*;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    //TODO orElse
    @Override
    public TaskDto save(CreateTaskRequest request, User acceptor) {
        User assignee = userRepository.findById(request.getAssigneeId())
                .orElse(null);
        User developer  = userRepository.findById(request.getDeveloperId())
                .orElse(null);
        User expert = userRepository.findById(request.getExpertId())
                .orElse(null);
        User reviewer = userRepository.findById(request.getReviewerId())
                .orElse(null);
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND + request.getProjectId()));

        Task task = mapper.toEntity(request, acceptor, assignee, developer, expert, reviewer, project);
//        TODO edit name
        task.setName(String.valueOf(Math.random()));
        task = repository.save(task);
        return mapper.toDto(task);
    }

    // TODO по статусам шығару керек
    @Override
    public List<TaskDto> getByUserAssigneeInWork(User user) {
        List<String> statuses = new ArrayList<>(List.of(
                TaskStatus.ANALYZING.name(),
                TaskStatus.IN_PROGRESS.name(),
                TaskStatus.WAITING.name(),
                TaskStatus.IN_REVIEW.name(),
                TaskStatus.DEPLOYMENT.name(),
                TaskStatus.ACCEPTANCE_TEST.name()
        ));
        return repository.findAllByAssigneeAndStatusIn(user, statuses)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + id));
    }
}
