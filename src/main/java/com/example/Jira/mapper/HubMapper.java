package com.example.Jira.mapper;

import com.example.Jira.entity.Hub;
import com.example.Jira.entity.Project;
import com.example.Jira.entity.User;
import com.example.Jira.model.requests.CreateHubRequest;
import com.example.Jira.model.HubDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HubMapper {

    public Hub toEntity(CreateHubRequest request, User user, Project project) {
        LocalDateTime updateTime = LocalDateTime.now();
        return Hub.builder()
                .content(request.getContent())
                .createdTime(updateTime)
                .name(request.getName())
                .updatedTime(updateTime)
                .creator(user)
                .lastUpdater(user)
                .project(project)
                .build();
    }

    public HubDto toDto(Hub hub) {
        return HubDto.builder()
                .id(hub.getId())
                .content(hub.getContent())
                .createdTime(hub.getCreatedTime())
                .name(hub.getName())
                .updatedTime(hub.getUpdatedTime())
                .creatorId(hub.getCreator().getId())
                .updaterId(hub.getLastUpdater().getId())
                .projectId(hub.getProject().getId())
                .updaterName(hub.getLastUpdater().getUserDetail().getName() + ' ' +
                        hub.getLastUpdater().getUserDetail().getSurname())
                .build();
    }

}
