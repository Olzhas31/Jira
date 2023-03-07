package com.example.Jira.exception;

public class ProjectNameAlreadyExistsException extends RuntimeException {
    public ProjectNameAlreadyExistsException(String message){
        super(message);
    }
}
