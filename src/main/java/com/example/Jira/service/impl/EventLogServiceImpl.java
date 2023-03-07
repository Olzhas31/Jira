package com.example.Jira.service.impl;

import com.example.Jira.entity.EventLog;
import com.example.Jira.entity.User;
import com.example.Jira.repository.EventLogRepository;
import com.example.Jira.service.IEventLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

    @Override
    public Page<EventLog> getAll(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<EventLog> logs = repository.findAll();
        Collections.sort(logs);

        List<EventLog> resultList;

        if (logs.size() < startItem) {
            resultList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, logs.size());
            resultList = logs.subList(startItem, toIndex);
        }

        return new PageImpl<>(resultList, PageRequest.of(currentPage, pageSize), logs.size());
    }
}
