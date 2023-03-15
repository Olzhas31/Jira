package com.example.Jira.exception;

public class UserBlockedException extends RuntimeException{

    public UserBlockedException(String message){
        super(message);
    }
}
