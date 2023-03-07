package com.example.Jira.repository;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    List<Sprint> findAllByProject(Project project);

    @Modifying
    @Query(value = " delete from tasks_sprints " +
            "    where sprint_id = :sprint_id",
            nativeQuery = true)
    void deleteTasksInSprint(@Param("sprint_id") Long sprintId);
}
