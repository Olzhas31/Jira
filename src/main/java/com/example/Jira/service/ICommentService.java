package com.example.Jira.service;

import com.example.Jira.entity.User;
import com.example.Jira.model.CommentDto;
import com.example.Jira.model.requests.CreateCommentRequest;

public interface ICommentService {

    CommentDto save(CreateCommentRequest request, User user);

}
