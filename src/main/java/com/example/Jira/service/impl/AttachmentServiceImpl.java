package com.example.Jira.service.impl;

import com.example.Jira.entity.Attachment;
import com.example.Jira.entity.Task;
import com.example.Jira.entity.User;
import com.example.Jira.entity.UserDetail;
import com.example.Jira.exception.EntityNotFoundException;
import com.example.Jira.repository.AttachmentRepository;
import com.example.Jira.repository.TaskRepository;
import com.example.Jira.service.IAttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.Jira.configuration.Constants.*;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements IAttachmentService {

    private final AttachmentRepository repository;
    private final TaskRepository taskRepository;

    @Transactional
    @Override
    public void save(User user, Long taskId, MultipartFile file) throws IOException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskId));

        String fileName = task.getId() + "_" + StringUtils.cleanPath(file.getOriginalFilename());

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = attachmentsPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("File saqtalmady: " + fileName, ioe);
        }

        Attachment attachment = Attachment.builder()
                .createdTime(LocalDateTime.now())
                .filename(fileName)
                .task(task)
                .user(user)
                .build();

        repository.save(attachment);
    }

    @Override
    public List<Attachment> getByTaskId(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskId));
        return repository.findAllByTask(task);
    }
}
