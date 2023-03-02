package com.example.Jira.controller;

import com.example.Jira.entity.states.Roles;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateUserRequest;
import com.example.Jira.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class UserController {

    private final IUserService service;

    // TODO add log
    // TODO only admins
    // TODO role front
    @PostMapping("/save-user")
    public String saveUser(CreateUserRequest request){
        request.setRole(Roles.BACKEND_DEVELOPER.name());
        UserDto userDto = service.save(request);
        return "redirect:/";
    }
}
