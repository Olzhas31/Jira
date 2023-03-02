package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.model.requests.CreateHubRequest;
import com.example.Jira.model.HubDto;
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

    @PostMapping("/save-hub")
    public String saveHub(CreateHubRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        HubDto hubDto = service.save(request, user);
        return "redirect:/";
    }

}
