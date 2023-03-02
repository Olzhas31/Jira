package com.example.Jira.service.impl;

import com.example.Jira.entity.Comment;
import com.example.Jira.entity.User;
import com.example.Jira.mapper.CommentMapper;
import com.example.Jira.model.CommentDto;
import com.example.Jira.model.requests.CreateCommentRequest;
import com.example.Jira.repository.CommentRepository;
import com.example.Jira.service.ICommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements ICommentService {

    private final CommentRepository repository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto save(CreateCommentRequest request, User user) {
        Comment comment = commentMapper.toEntity(request, user);
        comment = repository.save(comment);
        return commentMapper.toDto(comment);
    }
}
