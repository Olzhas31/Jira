package com.example.Jira.controller;

import com.example.Jira.entity.EventLog;
import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.service.IEventLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class MainController {

    private final IEventLogService eventLogService;

    @GetMapping( path = {"/home", "/"})
    public String showIndexPage(Model model, Authentication authentication){
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            if (user.getRole().equals(Roles.ADMIN.name())) {
                return "admin/admin";
            }
        }
        return "index";
    }

    @GetMapping("/login")
    public String showLoginAndRegistrationPage(){
        return "login";
    }

    @GetMapping("/admin/activity")
    public String showActivitiesPage(Model model,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);

        Page<EventLog> logPage = eventLogService.getAll(PageRequest.of(currentPage - 1, pageSize));

        int totalPages = logPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("logs", logPage);
        return "admin/activity";
    }

}
