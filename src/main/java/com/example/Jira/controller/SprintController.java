package com.example.Jira.controller;

import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.SprintDto;
import com.example.Jira.model.requests.CreateProjectRequest;
import com.example.Jira.model.requests.CreateSprintRequest;
import com.example.Jira.service.ISprintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class SprintController {

    private final ISprintService service;

    // TODO test
    // TODO add log
    // TODO only project manager
    @PostMapping("/save-sprint")
    public String saveSprint(CreateSprintRequest request){
        SprintDto sprintDto = service.save(request);
        return "redirect:/";
    }
}
