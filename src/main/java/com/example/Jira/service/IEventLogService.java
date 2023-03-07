package com.example.Jira.service;

import com.example.Jira.entity.EventLog;
import com.example.Jira.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEventLogService {

    void save(User user, String payload);

    Page<EventLog> getAll(Pageable pageable);
}
