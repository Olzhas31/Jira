package com.example.Jira.controller;

import com.example.Jira.entity.states.Priority;

public class Tests {
    public static void main(String[] args) {
        for(Priority priority:Priority.values()) {
            System.out.println(priority);
        }

    }
}
