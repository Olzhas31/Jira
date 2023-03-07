package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Priority;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.entity.states.TaskStatus;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.TaskDto;
import com.example.Jira.model.requests.CreateTaskRequest;
import com.example.Jira.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class TaskController {

    private final ITaskService service;
    private final IUserService userService;
    private final IProjectService projectService;
    private final IEventLogService log;
    private final ITaskHistoryService taskHistoryService;
    private final IAttachmentService attachmentService;
    private final ISprintService sprintService;

    @GetMapping("/save-task")
    public String showSaveTaskPage(Model model,
                                   @RequestParam("project-id") Long projectId) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("users",
                projectService.getUsersByProjectId(projectId));
        model.addAttribute("developers",
                projectService.getUsersByProjectIdAndRole(projectId, List.of(Roles.BACKEND_DEVELOPER, Roles.FRONTEND_DEVELOPER)));
        model.addAttribute("experts",
                projectService.getUsersByProjectIdAndRole(projectId, List.of(Roles.EXPERT)));
        return "create-task";
    }

    @PostMapping("/save-task")
    public String saveTask(CreateTaskRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        TaskDto taskDto = service.save(request, user);
        return "redirect:/task?id=" + taskDto.getId();
    }

    @GetMapping("/dashboard")
    public String showDashboardPage(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<ProjectDto> userProjects = projectService.getProjectsByUser(user);
        model.addAttribute("projects", userProjects);
        model.addAttribute("backlog", service.getBacklogTasks(userProjects));
        model.addAttribute("analyzing", service.getDashboardTasks(user, userProjects,
                List.of(TaskStatus.ANALYZING.name())));
        model.addAttribute("inProgress", service.getDashboardTasks(user, userProjects,
                List.of(TaskStatus.IN_PROGRESS.name())));
        model.addAttribute("waiting", service.getDashboardTasks(user, userProjects,
                List.of(TaskStatus.WAITING.name(), TaskStatus.IN_REVIEW.name(), TaskStatus.DEPLOYMENT.name(), TaskStatus.ACCEPTANCE_TEST.name())));
        model.addAttribute("deploy", service.getDashboardTasks(user, userProjects,
                List.of(TaskStatus.PRODUCTION_DEPLOYMENT.name())));
        model.addAttribute("done", service.getDashboardTasks(user, userProjects,
                List.of(TaskStatus.CLOSED.name(), TaskStatus.REJECTED_OR_CANCELED.name())));
        return "dashboard";
    }

    @GetMapping("/kanban")
    public String showKanbanPage(Model model, Authentication authentication,
                                 @RequestParam(value = "project-id", required = false) Long projectId,
                                 @RequestParam(name = "user-id", required = false) Long userId
    ) {
        User user = (User) authentication.getPrincipal();
        if (!Objects.isNull(userId)) {
            user = userService.getEntityById(userId);
        }
        if (Objects.isNull(projectId)) {
            List<ProjectDto> projects = projectService.getProjectsByUser(user);
            if (projects.size() == 0) {
                model.addAttribute("error", "Error message");
                return "kanban";
            } else {
                projectId = projects.get(0).getId();
            }
        }

        model.addAttribute("users",
                projectService.getUsersByProjectId(projectId));
        model.addAttribute("projectDto", projectService.getProjectById(projectId));
        model.addAttribute("backlog", service.getBacklogTasksByProjectId(projectId));
        model.addAttribute("processing", service.getAllByUserAndProjectIdAndStatusesAtActualSprint(user, projectId,
                List.of(TaskStatus.ANALYZING.name(), TaskStatus.IN_PROGRESS.name())));
        model.addAttribute("waiting", service.getAllByUserAndProjectIdAndStatusesAtActualSprint(user, projectId,
                List.of(TaskStatus.WAITING.name(), TaskStatus.IN_REVIEW.name(), TaskStatus.ACCEPTANCE_TEST.name(), TaskStatus.DEPLOYMENT.name())));
        model.addAttribute("deploying", service.getAllByUserAndProjectIdAndStatusesAtActualSprint(user, projectId,
                List.of(TaskStatus.PRODUCTION_DEPLOYMENT.name())));
        model.addAttribute("done", service.getAllByUserAndProjectIdAndStatusesAtActualSprint(user, projectId,
                List.of(TaskStatus.CLOSED.name(), TaskStatus.REJECTED_OR_CANCELED.name())));
        return "kanban";
    }

    @GetMapping("/task")
    public String showTask(Model model,
            @RequestParam("id") Long id) {
        TaskDto taskDto =  service.getById(id);
        model.addAttribute("taskDto", taskDto);
        model.addAttribute("history", taskHistoryService.getTaskHistoriesByTaskId(id));
        model.addAttribute("attachments", attachmentService.getByTaskId(id));
        model.addAttribute("usersByProject",
                projectService.getUsersByProjectId(taskDto.getProjectId()));
        model.addAttribute("developers",
                projectService.getUsersByProjectIdAndRole(taskDto.getProjectId(), List.of(Roles.BACKEND_DEVELOPER, Roles.FRONTEND_DEVELOPER)));
        model.addAttribute("experts",
                projectService.getUsersByProjectIdAndRole(taskDto.getProjectId(), List.of(Roles.EXPERT)));
        model.addAttribute("sprints",
                sprintService.getByProjectId(taskDto.getProjectId()));
        model.addAttribute("sprintsByTask", sprintService.getByTaskId(id));
        return "task";
    }

    @PostMapping("/edit-task")
    public String updateTask(
            @RequestParam("id") Long taskId,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "priority", required = false) String priority,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "assigneeId", required = false) Long assigneeId,
            @RequestParam(name = "expertId", required = false) Long expertId,
            @RequestParam(name = "developerId", required = false) Long developerId,
            @RequestParam(name = "reviewerId", required = false) Long reviewerId,
            @RequestParam(name = "dueDate", required = false) LocalDate dueDate
    ) {
        service.update(taskId, title, description, priority, status, assigneeId, expertId, developerId, reviewerId, dueDate);
        return "redirect:/task?id=" + taskId;
    }

}
