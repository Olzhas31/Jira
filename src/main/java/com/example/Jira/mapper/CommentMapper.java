package com.example.Jira.mapper;

import com.example.Jira.entity.Comment;
import com.example.Jira.entity.User;
import com.example.Jira.model.CommentDto;
import com.example.Jira.model.requests.CreateCommentRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentMapper {

    public Comment toEntity(CreateCommentRequest request, User user) {
        return Comment.builder()
                .commentType(request.getCommentType())
                .content(request.getContent())
                .createdTime(LocalDateTime.now())
                .user(user)
                .build();
    }

    public CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .commnentType(comment.getCommentType())
                .content(comment.getContent())
                .createdTime(comment.getCreatedTime())
                .userId(comment.getUser().getId())
                .build();
    }
}
