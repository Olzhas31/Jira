package com.example.Jira.mapper;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.Sprint;
import com.example.Jira.entity.Task;
import com.example.Jira.entity.User;
import com.example.Jira.entity.states.TaskStatus;
import com.example.Jira.model.TaskDto;
import com.example.Jira.model.requests.CreateTaskRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class TaskMapper {

    public TaskDto toDto(Task task) {
        String assigneeName;
        if (task.getAssignee() != null) {
            assigneeName = task.getAssignee().getUserDetail().getName() == null ?
                    " " : task.getAssignee().getUserDetail().getName();
            assigneeName += task.getAssignee().getUserDetail().getSurname() == null ?
                    " " : task.getAssignee().getUserDetail().getSurname();
        } else {
            assigneeName = null;
        }

        String expertName;
        if (task.getExpert() != null) {
            expertName = task.getExpert().getUserDetail().getName() == null ?
                    " " : task.getAssignee().getUserDetail().getName();
            expertName += task.getExpert().getUserDetail().getName() == null ?
                    " " : task.getAssignee().getUserDetail().getName();
        } else {
            expertName = null;
        }

        return TaskDto.builder()
                .id(task.getId())
                .createdTime(task.getCreatedTime())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .name(task.getName())
                .priority(task.getPriority())
                .resolvedTime(task.getResolvedTime())
                .startDate(task.getStartDate())
                .title(task.getTitle())
                .updatedTime(task.getUpdatedTime())
                .acceptorId(task.getAcceptor().getId())
                .assigneeId(task.getAssignee().getId())
                .assigneeName(assigneeName)
                .developerId(task.getDeveloper().getId())
                .expertId(task.getExpert().getId())
                .expertName(expertName)
                .reviewerId(task.getReviewer().getId())
                .status(task.getStatus())
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .build();
    }

    public Task toEntity(CreateTaskRequest request,
                         User acceptor,
                         User assignee,
                         User developer,
                         User expert,
                         User reviewer,
                         Project project) {
        LocalDateTime currentTime = LocalDateTime.now();
        return Task.builder()
                .createdTime(currentTime)
                .description(request.getDescription())
                .priority(request.getPriority())
                .title(request.getTitle())
                .updatedTime(currentTime)
                .acceptor(acceptor)
                .assignee(assignee)
                .developer(developer)
                .expert(expert)
                .reviewer(reviewer)
                .project(project)
                .status(TaskStatus.NEW.name())
                .build();
    }
}
