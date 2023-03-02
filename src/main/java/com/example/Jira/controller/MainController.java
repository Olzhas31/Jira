package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {

    private final ITaskService taskService;

    // TODO test getByUserAssignee
    @GetMapping("/home")
    public String showIndexPage(Model model, Authentication authentication){
//        User user = (User) authentication.getPrincipal();
//        model.addAttribute("tasks", taskService.getByUserAssigneeInWork(user));
        return "index";
    }

    @GetMapping("/login")
    public String showLoginAndRegistrationPage(){
        return "login";
    }

    // TODO only admin
    @GetMapping("/registration")
    public String showRegistrationPage(){
        return "registration";
    }

}
