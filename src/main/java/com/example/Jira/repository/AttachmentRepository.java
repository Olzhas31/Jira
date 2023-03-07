package com.example.Jira.repository;

import com.example.Jira.entity.Attachment;
import com.example.Jira.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findAllByTask(Task task);
}
