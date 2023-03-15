package com.example.Jira.mapper;

import com.example.Jira.entity.User;
import com.example.Jira.entity.UserDetail;
import com.example.Jira.model.UserDto;
import com.example.Jira.model.requests.CreateUserRequest;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class UserMapper {

    public UserDto toDto(User user) {
        UserDetail userDetail = user.getUserDetail();
        String role = user.getRole();
        if (user.getRole().equals("PROJECT_MANAGER")) {
            role = "Жоба жетекшісі";
        } else if (user.getRole().equals("EXPERT")) {
            role = "Сарапшы";
        } else if (user.getRole().equals("FRONTEND_DEVELOPER")) {
            role = "Frontend бағдарламаушы";
        } else if (user.getRole().equals("BACKEND_DEVELOPER")) {
            role = "Backend бағдарламаушы";
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(userDetail.getEmail())
                .info(userDetail.getInfo())
                .name(userDetail.getName())
                .phoneNumber(userDetail.getPhoneNumber())
                .surname(userDetail.getSurname())
                .urlPicture(userDetail.getUrlPicture())
                .role(role)
                .registerAt(user.getUserDetail().getRegisterDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .locked(user.getLocked())
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
