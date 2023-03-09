package com.example.Jira.service;

import com.example.Jira.entity.Attachment;
import com.example.Jira.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IAttachmentService {

    String save(User user, Long taskId, MultipartFile file) throws IOException;

    List<Attachment> getByTaskId(Long taskId);
}
