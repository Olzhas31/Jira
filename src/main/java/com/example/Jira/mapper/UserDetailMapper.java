package com.example.Jira.mapper;

import com.example.Jira.entity.UserDetail;
import com.example.Jira.model.requests.CreateUserRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserDetailMapper {

    public UserDetail toEntity(CreateUserRequest request) {
        return UserDetail.builder()
                .email(request.getEmail())
                .info(request.getInfo())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .surname(request.getSurname())
                .registerDate(LocalDate.now())
                .build();
    }
}
