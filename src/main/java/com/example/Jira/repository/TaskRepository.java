package com.example.Jira.repository;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.Task;
import com.example.Jira.entity.TaskHistory;
import com.example.Jira.entity.User;
import com.example.Jira.entity.states.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAssigneeAndProjectAndStatusIn(User user, Project project, List<String> status);

    List<Task> findAllByStatusIn(List<String> statuses);

    @Query(value = " select * from tasks " +
            "         where created_time = (select max(created_time) from tasks where project_id = ?1)",
            nativeQuery = true)
    Optional<Task> findLastTaskByProjectId(Long projectId);

    @Query(value = "select * from tasks " +
                    "         where status in ('TODO', 'NEW') " +
                    "            and project_id = ?1",
            nativeQuery = true)
    List<Task> findBacklogTasksByProjectId(Long projectId);
}
