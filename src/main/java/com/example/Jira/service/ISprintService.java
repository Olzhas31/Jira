package com.example.Jira.service;

import com.example.Jira.model.SprintDto;
import com.example.Jira.model.requests.CreateSprintRequest;

import java.util.List;

public interface ISprintService {
    SprintDto save(CreateSprintRequest request);

    List<SprintDto> getByProjectId(Long projectId);
}
