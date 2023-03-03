package com.example.Jira.service;

import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {

    UserDto save(CreateUserRequest request);

    List<UserDto> getAll();

    UserDto getById(Long id);

    void update(UserDto userDto);
}
