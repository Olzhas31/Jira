package com.example.Jira.service;

import com.example.Jira.entity.User;

public interface IEventLogService {

    void save(User user, String payload);

}
