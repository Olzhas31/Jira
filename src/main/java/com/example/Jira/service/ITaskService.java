package com.example.Jira.service;

import com.example.Jira.entity.User;
import com.example.Jira.model.TaskDto;
import com.example.Jira.model.requests.CreateTaskRequest;

import java.util.List;

public interface ITaskService {
    TaskDto save(CreateTaskRequest request, User user);

    List<TaskDto> getByUserAssigneeInWork(User user);

    TaskDto getById(Long id);
}
