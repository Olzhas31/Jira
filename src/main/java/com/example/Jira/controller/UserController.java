package com.example.Jira.controller;

import com.example.Jira.entity.User;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class UserController {

    private final IUserService service;
    private final UserMapper mapper;
    private final IEventLogService log;

    @GetMapping("/admin/create-user")
    public String showCreateUserPage(Model model) {
        model.addAttribute("roles", Roles.values());
        return "admin/create-user";
    }

    @PostMapping("/save-user")
    public String saveUser(Authentication authentication, CreateUserRequest request){
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to create user: " + request);
        UserDto userDto = service.save(request);
        log.save(user, "user created: " + userDto);
        return "redirect:/profile?id=" + userDto.getId();
    }

    @GetMapping("/profile")
    public String showProfilePage(Authentication authentication,
                                  Model model,
                                  @RequestParam(value = "id", required = false) Long id) throws InterruptedException, IOException {
        User user = (User) authentication.getPrincipal();
        UserDto userDto;
        if (Objects.isNull(id)) {
            userDto = mapper.toDto(user);
        } else {
            userDto = service.getById(id);
        }

        model.addAttribute("isYourself", id == null || id.equals(user.getId()));
        model.addAttribute("userDto", userDto);
        return "profile";
    }

    @GetMapping("/admin/users")
    public String showUsersPage(Model model) {
        model.addAttribute("users", service.getAll());
        return "admin/users";
    }

    @PostMapping("/update-user")
    public String updateUser(Authentication authentication, UserDto userDto) {
        User user = (User) authentication.getPrincipal();
        userDto.setId(user.getId());
        log.save(user, "request to update user: " + userDto);
        userDto = service.update(userDto);
        log.save(user, "user updated: " + userDto);
        return "redirect:/logout";
    }

    @GetMapping("/change-block")
    public String changeBlock(Authentication authentication, @RequestParam("id") Long id) {
        User user = (User) authentication.getPrincipal();
        log.save(user, "request to block by id: " + id);
        service.changeBlock(id);
        log.save(user, "user blocked by id: " + id);
        return "redirect:/admin/users";
    }

    @PostMapping("/edit-photo")
    public String uploadImage(Authentication authentication, @RequestParam("image") MultipartFile file) throws IOException {
        User user = (User) authentication.getPrincipal();
        service.editPhotoByUser(user, file);
        return "redirect:/logout";
    }
}
