package com.example.Jira.repository;

import com.example.Jira.entity.Task;
import com.example.Jira.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskHistoryRepository
        extends JpaRepository<TaskHistory, Long> {

    List<TaskHistory> findAllByTask(Task task);
}
