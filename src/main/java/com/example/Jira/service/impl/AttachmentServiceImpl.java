package com.example.Jira.service.impl;

import com.example.Jira.repository.AttachmentRepository;
import com.example.Jira.service.IAttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements IAttachmentService {

    private final AttachmentRepository repository;

    @Override
    public void save() {

    }
}
