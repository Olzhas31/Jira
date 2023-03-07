package com.example.Jira.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final String PROJECT_NOT_FOUND = "Project not found, id: ";
    public static final String HUB_NOT_FOUND = "Hub not found, id: ";
    public static final String USER_NOT_FOUND = "User not found, id: ";
    public static final String TASK_NOT_FOUND = "Task not found, id: ";
    public static final String SPRINT_NOT_FOUND = "Sprint not found, id: ";

    public static final String USERNAME_ALREADY_EXISTS = "Username is already exists, username: ";
    public static final String EMAIL_ALREADY_EXISTS = "Email is already exists, email: ";
    public static final String NAME_ALREADY_EXISTS = "Name is already exists, name: ";

    public static final Path uploadPath = Paths.get( "src/main/resources/static/avatars/");
    public static final Path attachmentsPath = Paths.get( "attachments");

    public static final String CREATE_TASK_HISTORY = "Task created.";
    public static final String UPDATE_TASK_HISTORY = "Task updated.";
}
