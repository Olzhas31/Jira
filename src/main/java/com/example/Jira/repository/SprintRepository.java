package com.example.Jira.repository;

import com.example.Jira.entity.Project;
import com.example.Jira.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

    List<Sprint> findAllByProject(Project project);
}
