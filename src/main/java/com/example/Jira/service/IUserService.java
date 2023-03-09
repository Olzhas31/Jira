package com.example.Jira.service;

import com.example.Jira.entity.User;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService extends UserDetailsService {

    UserDto save(CreateUserRequest request);

    List<UserDto> getAll();

    List<UserDto> getUsersIsNotExistsByProjectId(Long projectId);

    UserDto getById(Long id);

    UserDto update(UserDto userDto);

    void changeBlock(Long id);

    User getEntityById(Long userId);

    void editPhotoByUser(User user, MultipartFile file) throws IOException;
}
