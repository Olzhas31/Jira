package com.example.Jira.repository;

import com.example.Jira.entity.Hub;
import com.example.Jira.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HubRepository extends JpaRepository<Hub, Long> {

    List<Hub> findAllByProject(Project project);
}
