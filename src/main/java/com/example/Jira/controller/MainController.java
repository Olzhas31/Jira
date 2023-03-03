package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping( path = {"/home", "/"})
    public String showIndexPage(Model model, Authentication authentication){
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            if (user.getRole().equals(Roles.ADMIN.name())) {
                return "admin";
            }
        }
        return "index";
    }

    @GetMapping("/login")
    public String showLoginAndRegistrationPage(){
        return "login";
    }

    @GetMapping("/testing")
    public String test() {
        return "404";
    }

}
