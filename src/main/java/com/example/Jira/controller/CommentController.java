package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.model.CommentDto;
import com.example.Jira.model.requests.CreateCommentRequest;
import com.example.Jira.service.ICommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class CommentController {

    private final ICommentService service;

    // TODO test
    @PostMapping("/save-comment")
    public String saveComment(CreateCommentRequest request, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        CommentDto commentDto = service.save(request, user);
        return "redirect:/";
    }
}
