package com.example.Jira.service.impl;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.Sprint;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.mapper.SprintMapper;
import com.example.Jira.model.SprintDto;
import com.example.Jira.model.requests.CreateSprintRequest;
import com.example.Jira.repository.ProjectRepository;
import com.example.Jira.repository.SprintRepository;
import com.example.Jira.service.ISprintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.Jira.configuration.Constants.PROJECT_NOT_FOUND;

@Service
@AllArgsConstructor
public class SprintServiceImpl implements ISprintService {

    private final SprintRepository repository;
    private final SprintMapper mapper;
    private final ProjectRepository projectRepository;

    @Override
    public SprintDto save(CreateSprintRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND + request.getProjectId()));
        Sprint sprint = mapper.toEntity(request, project);
        sprint = repository.save(sprint);
        return mapper.toDto(sprint);
    }

    @Override
    public List<SprintDto> getByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(PROJECT_NOT_FOUND + projectId));
        return repository.findAllByProject(project)
                .stream().map(mapper::toDto)
                .toList();
    }
}
