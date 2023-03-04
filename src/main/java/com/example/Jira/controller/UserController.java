package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Priority;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.mapper.UserMapper;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateUserRequest;
import com.example.Jira.service.IEventLogService;
import com.example.Jira.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@AllArgsConstructor
public class UserController {

    private final IUserService service;
    private final UserMapper mapper;
    private final IEventLogService log;

    @GetMapping("/save-user")
    public String showCreateUserPage(Model model) {
        model.addAttribute("roles", Roles.values());
        return "create-user";
    }

    @PostMapping("/save-user")
    public String saveUser(CreateUserRequest request){
        UserDto userDto = service.save(request);
        return "redirect:/profile?id=" + userDto.getId();
    }

    @GetMapping("/profile")
    public String showProfilePage(Authentication authentication,
                                  Model model,
                                  @RequestParam(value = "id", required = false) Long id) {
        User user = (User) authentication.getPrincipal();
        UserDto userDto = null;
        if (Objects.isNull(id)) {
            userDto = mapper.toDto(user);
        } else {
            userDto = service.getById(id);
        }

        model.addAttribute("isYourself", id == null || id.equals(user.getId()));
        model.addAttribute("userDto", userDto);
        return "profile";
    }

//    TODO каждый может только себя
    @PostMapping("/update-user")
    public String updateUser(UserDto userDto) {
        service.update(userDto);
        return "redirect:/logout";
    }
}
