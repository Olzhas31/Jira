package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.model.requests.CreateHubRequest;
import com.example.Jira.model.HubDto;
import com.example.Jira.service.IEventLogService;
import com.example.Jira.service.IHubService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class HubController {

    private final IHubService service;
    private final IEventLogService log;

    @PostMapping("/save-hub")
    public String saveHub(CreateHubRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to create new hub. Request: " + request);
        HubDto hubDto = service.save(request, user);
        log.save(user, "created new hub. HubDto: " + hubDto);
        return "redirect:/";
    }

}
