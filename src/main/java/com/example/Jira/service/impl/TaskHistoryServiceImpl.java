package com.example.Jira.service.impl;

import com.example.Jira.entity.Task;
import com.example.Jira.entity.TaskHistory;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.repository.TaskHistoryRepository;
import com.example.Jira.repository.TaskRepository;
import com.example.Jira.service.ITaskHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.example.Jira.configuration.Constants.TASK_NOT_FOUND;

@Service
@AllArgsConstructor
public class TaskHistoryServiceImpl implements ITaskHistoryService {

    private final TaskHistoryRepository repository;
    private final TaskRepository taskRepository;

    @Override
    public List<TaskHistory> getTaskHistoriesByTaskId(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->new EntityNotFoundException(TASK_NOT_FOUND + taskId));
//        TODO Collections.sort(task.get);
        return repository.findAllByTask(task);
    }
}
