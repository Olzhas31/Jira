package com.example.Jira.repository;

import com.example.Jira.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskHistoryRepository
        extends JpaRepository<TaskHistory, Long> {
}
