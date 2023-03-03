package com.example.Jira.controller;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Priority;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.SprintDto;
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

    // TODO add name
    @PostMapping("/save-task")
    public String saveTask(CreateTaskRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
//        TODO ?????
        request.setProjectId(2L);
        TaskDto taskDto = service.save(request, user);
        return "redirect:/";
    }

    // TODO only user projects
    @GetMapping("/dashboard")
    public String showDashboardPage(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("projects", projectService.getProjectsByUser(user));
        return "dashboard";
    }

    @GetMapping("/kanban")
    public String showKanbanPage(Model model) {
//        model.addAttribute("projects", projectService.getAll());
        return "kanban";
    }

    @GetMapping("/task")
    public String showTask(Model model,
            @RequestParam("id") Long id) {
        model.addAttribute("taskDto", service.getById(id));
        return "task";
    }

}
