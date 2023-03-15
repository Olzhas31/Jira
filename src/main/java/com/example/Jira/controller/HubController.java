package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.model.requests.CreateHubRequest;
import com.example.Jira.model.HubDto;
import com.example.Jira.service.IEventLogService;
import com.example.Jira.service.IHubService;
import com.example.Jira.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class HubController {

    private final IHubService service;
    private final IEventLogService log;
    private final ITaskService taskService;

    @PostMapping("/save-hub")
    public String saveHub(CreateHubRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to create new hub. Request: " + request);
        HubDto hubDto = service.save(request, user);
        log.save(user, "created new hub. HubDto: " + hubDto);
        return "redirect:/hub/" + hubDto.getId();
    }

    @GetMapping("/hub/{id}")
    public String showHubPage(@PathVariable Long id, Model model) {
        model.addAttribute("hub", service.getById(id));
        model.addAttribute("tasks", taskService.getTasksByHubId(id));
        return "hub";
    }

    @PostMapping("/edit-hub")
    public String updateHub(Authentication authentication,
                            @RequestParam("id") Long id,
                            @RequestParam("name") String name,
                            @RequestParam("content") String content) {
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to edit hub. Request: id=" + id + ", name=" + name + ", content=" + content);
        HubDto hubDto = service.update(id, name, content, user);
        log.save(user, "hub updated. HubDto: " + hubDto);
        return "redirect:/hub/" + id;
    }

}
