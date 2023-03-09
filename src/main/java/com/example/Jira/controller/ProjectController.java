package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.AddUserToProjectRequest;
import com.example.Jira.model.requests.CreateProjectRequest;
import com.example.Jira.model.requests.UpdateProjectRequest;
import com.example.Jira.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@AllArgsConstructor
public class ProjectController {

    private final IProjectService service;
    private final IUserService userService;
    private final IHubService hubService;
    private final ISprintService sprintService;
    private final IEventLogService log;

    @PostMapping("/save-project")
    public String saveProject(CreateProjectRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to create new project. Request: " + request);
        ProjectDto projectDto = service.save(request, user);
        log.save(user, "created new project. ProjectDto: " + projectDto);

        return "redirect:/projects/" + projectDto.getId();
    }

    @GetMapping("/projects")
    public String showProjectsPage(Model model, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        model.addAttribute("isRolePM", user.getRole().equals(Roles.PROJECT_MANAGER.name()));
        model.addAttribute("projects", service.getAll());
        return "projects";
    }

    @GetMapping("/projects/{id}")
    public String showProjectPage(Model model, Authentication authentication,
                                  @PathVariable Long id){
        User user = (User) authentication.getPrincipal();

        model.addAttribute("isRolePM", user.getRole().equals(Roles.PROJECT_MANAGER.name()));
        model.addAttribute("projectId", id);
        model.addAttribute("usersByProject", service.getUsersByProjectId(id));
        model.addAttribute("hubs", hubService.getByProjectId(id));
        model.addAttribute("sprints", sprintService.getByProjectId(id));
        model.addAttribute("project", service.getProjectById(id));
        model.addAttribute("users", userService.getUsersIsNotExistsByProjectId(id));

        return "project";
    }

    @PostMapping("/update-project")
    public String updateProject(UpdateProjectRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to update project. Request: " + request);
        ProjectDto projectDto = service.update(request);
        log.save(user, "updated project. ProjectDto: " + projectDto);

        return "redirect:/projects/" + request.getId();
    }

    @PostMapping("/add-user-to-project")
    public String addUserToProject(AddUserToProjectRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to add user to project. Request: " + request);
        Map<ProjectDto, UserDto> response = service.addUserToProject(request.getUserId(), request.getProjectId());
        log.save(user, "user added to project. " + response.entrySet());
        return "redirect:/projects/" + request.getProjectId();
    }

    @PostMapping("/delete-user-in-project")
    public String deleteUserInProject(AddUserToProjectRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to delete user in project. Request: " + request);
        service.deleteUserInProject(request.getProjectId(), request.getUserId());
        log.save(user, "user deleted in project. Request: " + request);
        return "redirect:/projects/" + request.getProjectId();
    }
}
