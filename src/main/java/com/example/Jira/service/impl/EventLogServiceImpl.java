package com.example.Jira.service.impl;

import com.example.Jira.repository.EventLogRepository;
import com.example.Jira.service.IEventLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventLogServiceImpl implements IEventLogService {

    private final EventLogRepository repository;
}
