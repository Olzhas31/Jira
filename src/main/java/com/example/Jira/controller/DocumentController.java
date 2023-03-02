package com.example.Jira.controller;

import com.example.Jira.service.IDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class DocumentController {

    private final IDocumentService service;

}
