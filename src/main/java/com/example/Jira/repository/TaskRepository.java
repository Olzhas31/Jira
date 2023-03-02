package com.example.Jira.repository;

import com.example.Jira.entity.Task;
import com.example.Jira.entity.TaskHistory;
import com.example.Jira.entity.User;
import com.example.Jira.entity.states.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAssigneeAndStatusIn(User user, List<String> status);
}
