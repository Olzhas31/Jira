package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Priority;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.entity.states.TaskStatus;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.TaskDto;
import com.example.Jira.model.requests.CreateTaskRequest;
import com.example.Jira.service.IEventLogService;
import com.example.Jira.service.IProjectService;
import com.example.Jira.service.ITaskService;
import com.example.Jira.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class TaskController {

    private final ITaskService service;
    private final IUserService userService;
    private final IProjectService projectService;
    private final IEventLogService log;

    @GetMapping("/save-task")
    public String showSaveTaskPage(Model model,
                                   @RequestParam("project-id") Long projectId) {
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
//        TODO ?????
        request.setProjectId(4L);
        TaskDto taskDto = service.save(request, user);
        return "redirect:/";
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
                                 @RequestParam(value = "project-id", required = false) Long projectId) {
        User user = (User) authentication.getPrincipal();
        if (Objects.isNull(projectId)) {
            List<ProjectDto> projects = projectService.getProjectsByUser(user);
            if (projects.size() == 0) {
                model.addAttribute("error", "Error message");
                return "kanban";
            } else {
                projectId = projects.get(0).getId();
            }
        }

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
        model.addAttribute("taskDto", service.getById(id));
        return "task";
    }

}
