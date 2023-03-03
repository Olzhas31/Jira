package com.example.Jira.service.impl;

import com.example.Jira.entity.EventLog;
import com.example.Jira.entity.User;
import com.example.Jira.repository.EventLogRepository;
import com.example.Jira.service.IEventLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EventLogServiceImpl implements IEventLogService {

    private final EventLogRepository repository;

    @Override
    public void save(User user, String payload) {
        EventLog log = EventLog.builder()
                .createdTime(LocalDateTime.now())
                .payload(payload)
                .user(user)
                .build();
        repository.save(log);
    }
}
