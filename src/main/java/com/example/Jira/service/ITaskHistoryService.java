package com.example.Jira.service;

import com.example.Jira.entity.TaskHistory;

import java.util.List;

public interface ITaskHistoryService {

    List<TaskHistory> getTaskHistoriesByTaskId(Long taskId);
}
