package com.example.Jira.service;

import com.example.Jira.entity.User;
import com.example.Jira.model.requests.CreateHubRequest;
import com.example.Jira.model.HubDto;

import java.util.List;

public interface IHubService {

    HubDto save(CreateHubRequest request, User user);

    HubDto getById(Long id);

    List<HubDto> getByProjectId(Long projectId);
}
