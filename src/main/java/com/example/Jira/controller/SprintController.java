package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.model.SprintDto;
import com.example.Jira.model.requests.CreateSprintRequest;
import com.example.Jira.service.IEventLogService;
import com.example.Jira.service.ISprintService;
import com.example.Jira.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class SprintController {

    private final ISprintService service;
    private final IEventLogService log;
    private final ITaskService taskService;

    @PostMapping("/save-sprint")
    public String saveSprint(CreateSprintRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to create new sprint. Request: " + request);
        SprintDto sprintDto = service.save(request);
        log.save(user, "created new sprint. Sprint: " + sprintDto);
        return "redirect:/sprint/" + sprintDto.getId();
    }

    @GetMapping("/sprint/{id}")
    public String showSprintPage(@PathVariable Long id, Model model) {
        model.addAttribute("sprint", service.getById(id));
        model.addAttribute("tasks", taskService.getTasksBySprintId(id));
        return "sprint";
    }

    // TODO only pm
    @PostMapping("/edit-sprint")
    public String updateSprint(
            Authentication authentication,
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("startDate")LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to create new sprint. Request: " + " id=" + id + ", name="
                + name + ", startDate=" + startDate + ", endDate=" + endDate);
        SprintDto sprintDto = service.update(id, name, startDate, endDate);
        log.save(user, "updated sprint: " + sprintDto);
        return "redirect:/sprint/" + id;
    }

    // TODO only pm
    @GetMapping("/delete-sprint/{id}")
    public String deleteSprint(@PathVariable(name = "id") Long sprintId) {
        Long projectId = service.deleteById(sprintId);
        return "redirect:/projects/" + projectId;
    }

    // TODO only pm
    @PostMapping("/add-task-to-sprint")
    public String addTaskToSprint(
            Authentication authentication,
            @RequestParam("taskId") Long taskId,
            @RequestParam("sprintId") Long sprintId) {
        User user = (User) authentication.getPrincipal();
        log.save(user, "request add task to sprint. Task id:" + taskId + ", sprint id=" + sprintId);
        service.addTaskToSprint(taskId, sprintId);
        log.save(user, "task added to sprint. Task id:" + taskId + ", sprint id=" + sprintId);
        return "redirect:/task?id=" + taskId;
    }

    // TODO only pm
    @PostMapping("/delete-task-in-sprint")
    public String deleteTaskInSprint(
            Authentication authentication,
            @RequestParam("taskId") Long taskId,
            @RequestParam("sprintId") Long sprintId) {
        User user = (User) authentication.getPrincipal();
        log.save(user, "request delete task in sprint. Task id:" + taskId + ", sprint id=" + sprintId);
        service.deleteTaskInSprint(taskId, sprintId);
        log.save(user, "task deleted in sprint. Task id:" + taskId + ", sprint id=" + sprintId);
        return "redirect:/task?id=" + taskId;
    }
}
