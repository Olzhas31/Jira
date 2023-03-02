package com.example.Jira.service.impl;

import com.example.Jira.entity.TaskHistory;
import com.example.Jira.repository.TaskHistoryRepository;
import com.example.Jira.service.ITaskHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskHistoryServiceImpl implements ITaskHistoryService {

    private final TaskHistoryRepository repository;
}
