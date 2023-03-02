package com.example.Jira.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String password;
    private String username;

    private String email;
    private String info;
    private String name;
    private String phoneNumber;
    private String surname;

    private String role;
}
