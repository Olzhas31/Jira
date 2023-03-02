package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.AddUserToProjectRequest;
import com.example.Jira.model.requests.CreateHubRequest;
import com.example.Jira.model.HubDto;
import com.example.Jira.model.requests.CreateProjectRequest;
import com.example.Jira.service.IHubService;
import com.example.Jira.service.IProjectService;
import com.example.Jira.service.ISprintService;
import com.example.Jira.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProjectController {

    private final IProjectService service;
    private final IUserService userService;
    private final IHubService hubService;
    private final ISprintService sprintService;

    // TODO only pm
    @PostMapping("/save-project")
    public String saveProject(CreateProjectRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        ProjectDto projectDto = service.save(request, user);
        return "redirect:/";
    }

    @GetMapping("/projects")
    public String showProjectsPage(Model model){
        model.addAttribute("projects", service.getAll());
        return "projects";
    }

    @GetMapping("/projects/{id}")
    public String showProjectPage(Model model, @PathVariable Long id){
        List<UserDto> allUsers = userService.getAll();

        model.addAttribute("projectId", id);
        model.addAttribute("usersByProject", service.getUsersByProjectId(id));
        model.addAttribute("hubs", hubService.getByProjectId(id));
        model.addAttribute("sprints", sprintService.getByProjectId(id));

//        TODO удалить людей которые уже есть в проекте или выбросит сообщение что человек уже есть
        model.addAttribute("users", allUsers);

        return "project";
    }

    //    TODO only pm
    @PostMapping("/add-user-to-project")
    public String addUserToProject(AddUserToProjectRequest request) {
        service.addUserToProject(request.getUserId(), request.getProjectId());
        return "redirect:/";
    }
}
