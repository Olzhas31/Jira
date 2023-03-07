package com.example.Jira.mapper;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.Task;
import com.example.Jira.entity.User;
import com.example.Jira.entity.states.TaskStatus;
import com.example.Jira.model.TaskDto;
import com.example.Jira.model.requests.CreateTaskRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TaskMapper {

    public TaskDto toDto(Task task) {
        String assigneeName = null;
        if (task.getAssignee() != null) {
            assigneeName = task.getAssignee().getUserDetail().getName() +
                    " " + task.getAssignee().getUserDetail().getSurname();
        }

        String expertName = null;
        if (task.getExpert() != null) {
            expertName = task.getExpert().getUserDetail().getName() +
                    " " + task.getExpert().getUserDetail().getSurname();
        }

        String developerName = null;
        if (task.getDeveloper() != null) {
            developerName = task.getDeveloper().getUserDetail().getName() +
                    " " + task.getDeveloper().getUserDetail().getSurname();
        }

        String reviewerName = null;
        if (task.getReviewer() != null) {
            reviewerName = task.getReviewer().getUserDetail().getName() +
                    " " + task.getReviewer().getUserDetail().getSurname();
        }

        String acceptorName = null;
        if (task.getAcceptor() != null) {
            acceptorName = task.getAcceptor().getUserDetail().getName() +
                    " " + task.getAcceptor().getUserDetail().getSurname();
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
                .acceptorName(acceptorName)
                .assigneeId(task.getAssignee() == null ?
                        null : task.getAssignee().getId())
                .assigneeName(assigneeName)
                .developerId(task.getDeveloper() == null ?
                        null : task.getDeveloper().getId())
                .developerName(developerName)
                .expertId(task.getExpert().getId())
                .expertName(expertName)
                .reviewerId(task.getReviewer() == null ?
                        null : task.getReviewer().getId())
                .reviewerName(reviewerName)
                .status(task.getStatus())
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .hubName(Objects.isNull(task.getHub()) ? null: task.getHub().getName())
                .hubId(Objects.isNull(task.getHub()) ? null: task.getHub().getId())
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
