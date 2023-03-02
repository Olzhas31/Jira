package com.example.Jira.controller;

import com.example.Jira.service.IAttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AttachmentController {

    private final IAttachmentService service;

    // TODO file
    @PostMapping("/save-attachment")
    public String saveAttachment() {
        return "redirect:/";
    }
}
