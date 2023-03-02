package com.example.Jira.service.impl;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.User;
import com.example.Jira.entity.states.Roles;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.mapper.ProjectMapper;
import com.example.Jira.mapper.UserMapper;
import com.example.Jira.model.ProjectDto;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateProjectRequest;
import com.example.Jira.repository.ProjectRepository;
import com.example.Jira.repository.UserRepository;
import com.example.Jira.service.IProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.Jira.configuration.Constants.PROJECT_NOT_FOUND;
import static com.example.Jira.configuration.Constants.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProjectRepository repository;
    private final UserRepository userRepository;
    private final ProjectMapper mapper;
    private final UserMapper userMapper;

    @Override
    public ProjectDto save(CreateProjectRequest request, User pm) {
        Project project = mapper.toEntity(request, pm);
        project = repository.save(project);
        return mapper.toDto(project);
    }

    @Override
    public List<ProjectDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDto getProjectById(Long projectId) {
        return repository.findById(projectId)
                .map(mapper::toDto)
                .orElseThrow(()-> new EntityNotFoundException(PROJECT_NOT_FOUND + projectId));
    }

    @Override
    public List<UserDto> getUsersByProjectId(Long projectId) {
        Project project = repository.findById(projectId)
                .orElseThrow(()-> new EntityNotFoundException(PROJECT_NOT_FOUND + projectId));
        return project.getUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> getUsersByProjectIdAndRole(Long projectId, List<Roles> roles) {
        Project project = repository.findById(projectId)
                .orElseThrow(()-> new EntityNotFoundException(PROJECT_NOT_FOUND + projectId));
        return project.getUsers()
                .stream()
                .filter(user -> {
                    for (Roles role : roles) {
                        if (user.getRole().equals(role.name())) {
                            return true;
                        }
                    }
                    return false;
                })
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addUserToProject(Long userId, Long projectId) {
        Project project = repository.findById(projectId)
                .orElseThrow(()-> new EntityNotFoundException(PROJECT_NOT_FOUND + projectId));
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND + projectId));

        project.getUsers().add(user);
        repository.save(project);
    }
}
