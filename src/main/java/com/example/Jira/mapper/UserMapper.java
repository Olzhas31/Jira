package com.example.Jira.mapper;

import com.example.Jira.entity.User;
import com.example.Jira.entity.UserDetail;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto toDto(User user) {
        UserDetail userDetail = user.getUserDetail();
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(userDetail.getEmail())
                .info(userDetail.getInfo())
                .name(userDetail.getName())
                .phoneNumber(userDetail.getPhoneNumber())
                .surname(userDetail.getSurname())
                .urlPicture(userDetail.getUrlPicture())
                .build();
    }

    public User toEntity(CreateUserRequest request, UserDetail userDetail) {
        return User.builder()
                .password(request.getPassword())
                .username(request.getUsername())
                .enabled(true)
                .locked(false)
                .role(request.getRole())
                .userDetail(userDetail)
                .build();
    }
}
