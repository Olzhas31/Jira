package com.example.Jira.service.impl;

import com.example.Jira.entity.Hub;
import com.example.Jira.entity.Project;
import com.example.Jira.entity.User;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.mapper.HubMapper;
import com.example.Jira.model.requests.CreateHubRequest;
import com.example.Jira.model.HubDto;
import com.example.Jira.repository.HubRepository;
import com.example.Jira.repository.ProjectRepository;
import com.example.Jira.service.IHubService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.Jira.configuration.Constants.HUB_NOT_FOUND;
import static com.example.Jira.configuration.Constants.PROJECT_NOT_FOUND;

@Service
@AllArgsConstructor
public class HubServiceImpl implements IHubService {

    private final HubRepository repository;
    private final HubMapper mapper;
    private final ProjectRepository projectRepository;

    @Override
    public HubDto save(CreateHubRequest request, User user) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND + request.getProjectId()));
        Hub hub = mapper.toEntity(request, user, project);
        hub = repository.save(hub);
        return mapper.toDto(hub);
    }

    @Override
    public HubDto getById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(HUB_NOT_FOUND + id)));
    }

    @Override
    public List<HubDto> getByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND + projectId));
        return repository.findAllByProject(project)
                .stream().map(mapper::toDto)
                .toList();
    }
}
