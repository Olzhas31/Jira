package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.SprintDto;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateSprintRequest;
import com.example.Jira.service.IEventLogService;
import com.example.Jira.service.ISprintService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@AllArgsConstructor
public class SprintController {

    private final ISprintService service;
    private final IEventLogService log;

    @PostMapping("/save-sprint")
    public String saveSprint(CreateSprintRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to create new sprint. Request: " + request);
        SprintDto sprintDto = service.save(request);
        log.save(user, "created new sprint. Sprint: " + sprintDto);

        return "redirect:/projects/" + sprintDto.getProjectId();
    }
}
