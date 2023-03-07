package com.example.Jira.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;

    private String email;
    private String info;
    private String name;
    private String phoneNumber;
    private String surname;
    private volatile String urlPicture;
    private String role;
    private String registerAt;
    private Boolean locked;
}
