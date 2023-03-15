package com.example.Jira.service.impl;

import com.example.Jira.entity.*;
import com.example.Jira.entity.states.TaskStatus;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.mapper.TaskMapper;
import com.example.Jira.model.DashboardResponse;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.TaskDto;
import com.example.Jira.model.requests.CreateTaskRequest;
import com.example.Jira.repository.*;
import com.example.Jira.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.Jira.configuration.Constants.*;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final HubRepository hubRepository;
    private final TaskHistoryRepository taskHistoryRepository;

    @Transactional
    @Override
    public TaskDto save(CreateTaskRequest request, User acceptor) {
        User assignee = null;
        if (!Objects.isNull(request.getAssigneeId())) {
            assignee = userRepository.findById(request.getAssigneeId())
                    .orElse(null);
        }
        User developer = null;
        if (request.getDeveloperId() != null) {
            developer = userRepository.findById(request.getDeveloperId())
                    .orElse(null);
        }
        User expert = userRepository.findById(request.getExpertId())
                .orElse(null);
        User reviewer = null;
        if (!Objects.isNull(request.getReviewerId())) {
            reviewer = userRepository.findById(request.getReviewerId())
                    .orElse(null);
        }

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND + request.getProjectId()));

        Task task = mapper.toEntity(request, acceptor, assignee, developer, expert, reviewer, project);
        task.setName(generateName(project));
        task = repository.save(task);

        taskHistoryRepository.save(
                TaskHistory.builder()
                        .transition(CREATE_TASK_HISTORY + request)
                        .createdTime(LocalDateTime.now())
                        .task(task)
                        .build()
        );
        return mapper.toDto(task);
    }

    @Override
    public List<TaskDto> getAllByUserAndProjectIdAndStatusesAtActualSprint(User user, Long projectId, List<String> statuses) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND + projectId));

        return repository.findAllByAssigneeAndProjectAndStatusIn(user, project, statuses)
                .stream()
                .filter(task -> {
                    for (Sprint sprint: task.getSprints()) {
                        if (sprint.getStartDate().isBefore(LocalDate.now()) && sprint.getEndDate().isAfter(LocalDate.now())) {
                            return true;
                        }
                    }
                    return false;
                })
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + id));
    }

    @Override
    public List<DashboardResponse> getBacklogTasks(List<ProjectDto> projects) {
        List<Task> tasks = repository.findAllByStatusIn(List.of(
                TaskStatus.ZHANA.name(), TaskStatus.TODO.name()
        ));
        List<DashboardResponse> result = new ArrayList<>();
        for (ProjectDto projectDto: projects) {
            result.add(DashboardResponse.builder()
                            .projectId(projectDto.getId())
                            .projectName(projectDto.getName())
                            .taskSize(0)
                    .build());
        }
        for (Task task : tasks) {
            for (DashboardResponse project: result) {
                if (Objects.equals(task.getProject().getId(), project.getProjectId())) {
                    project.setTaskSize(project.getTaskSize() + 1);
                }
            }
        }
        return result;
    }

    @Override
    public List<DashboardResponse> getDashboardTasks(User user, List<ProjectDto> projects, List<String> taskStatues) {
        List<Task> tasks = repository.findAllByStatusIn(taskStatues);
        List<DashboardResponse> result = new ArrayList<>();
        for (ProjectDto projectDto: projects) {
            result.add(DashboardResponse.builder()
                    .projectId(projectDto.getId())
                    .projectName(projectDto.getName())
                    .taskSize(0)
                    .build());
        }
        for (Task task : tasks) {
            for (DashboardResponse project: result) {
                if (Objects.equals(task.getProject().getId(), project.getProjectId())) {
                    if (Objects.equals(task.getAssignee(), user) || Objects.equals(task.getDeveloper(), user)
                            || Objects.equals(task.getExpert(), user) || Objects.equals(task.getReviewer(), user)) {
                        for (Sprint sprint: task.getSprints()) {
                            if (sprint.getStartDate().isBefore(LocalDate.now()) && sprint.getEndDate().isAfter(LocalDate.now())) {
                                project.setTaskSize(project.getTaskSize() + 1);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<TaskDto> getBacklogTasksByProjectId(Long projectId) {
        return repository.findBacklogTasksByProjectId(projectId)
                .stream().map(mapper::toDto)
                .toList();
    }

    @Override
    public TaskDto update(Long taskId, String title, String description, String priority, String status,
                       Long assigneeId, Long expertId, Long developerId, Long reviewerId,
                       LocalDate dueDate, Long hubId
    ) {
        Task task = repository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskId));

        User assignee = null;
        if (!Objects.isNull(assigneeId)) {
            assignee = userRepository.findById(assigneeId)
                    .orElse(null);
        }
        User developer = null;
        if (!Objects.isNull(developerId)) {
            developer = userRepository.findById(developerId)
                    .orElse(null);
        }
        User expert = userRepository.findById(expertId)
                .orElse(null);
        User reviewer = null;
        if (!Objects.isNull(reviewerId)) {
            reviewer = userRepository.findById(reviewerId)
                    .orElse(null);
        }

        LocalDateTime now = LocalDateTime.now();
        if (status.equalsIgnoreCase(TaskStatus.QABYLDANBADY_NEMESE_JOIYLDY.name()) ||
                status.equalsIgnoreCase(TaskStatus.JABYLDY.name())) {
            if (Objects.isNull(task.getResolvedTime())) {
                task.setResolvedTime(now);
            }
        }

        if (!Objects.isNull(hubId)) {
            Hub hub = hubRepository.findById(hubId)
                    .orElseThrow(() -> new EntityNotFoundException(HUB_NOT_FOUND + hubId));
            task.setHub(hub);
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority);
        task.setStatus(status);
        task.setAssignee(assignee);
        task.setDeveloper(developer);
        task.setExpert(expert);
        task.setReviewer(reviewer);
        task.setDueDate(dueDate);
        task.setUpdatedTime(now);

        task = repository.save(task);

        taskHistoryRepository.save(
                TaskHistory.builder()
                        .transition(UPDATE_TASK_HISTORY + task)
                        .createdTime(LocalDateTime.now())
                        .task(task)
                        .build()
        );
        return mapper.toDto(task);
    }

    @Override
    public List<TaskDto> getTasksByHubId(Long hubId) {
        Hub hub = hubRepository.findById(hubId)
                .orElseThrow(() -> new EntityNotFoundException(HUB_NOT_FOUND + hubId));
        return repository.findAllByHub(hub)
                .stream().map(mapper::toDto)
                .toList();
    }

    @Override
    public List<TaskDto> getTasksBySprintId(Long id) {
        return repository.findAllBySprint(id)
                .stream().map(mapper::toDto)
                .toList();
    }

    private String generateName(Project project) {
        Task lastTask = repository.findLastTaskByProjectId(project.getId())
                .orElse(null);
        if (Objects.isNull(lastTask)) {
            return project.getName() + "-1";
        }
        String[] name = lastTask.getName().split("-");
        return project.getName() + "-" + (Integer.parseInt(name[1]) + 1);
    }
}
