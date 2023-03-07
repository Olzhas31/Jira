package com.example.Jira.controller;

import com.example.Jira.entity.User;
import com.example.Jira.service.IAttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.Jira.configuration.Constants.attachmentsPath;

@Controller
@AllArgsConstructor
public class AttachmentController {

    private final IAttachmentService service;

    @PostMapping("/add-attachment")
    public String saveAttachment(
            Authentication authentication,
            @RequestParam("taskId") Long taskId,
            @RequestParam("image") MultipartFile file) throws IOException {
        User user = (User) authentication.getPrincipal();

        service.save(user, taskId, file);
        return "redirect:/task?id=" + taskId;
    }

//    @GetMapping("/download-attachment/{name}")
//    public void downloadFile(@PathVariable("name") String filename,
//                             Model model,
//                             HttpServletResponse response) throws IOException {
//        response.setContentType("application/octet-stream");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename = " + filename;
//        response.setHeader(headerKey, headerValue);
//        ServletOutputStream outputStream = response.getOutputStream();
//
//        File file = new File(attachmentsPath + "/" + filename);
//        byte[] bytes = new byte[(int) file.length()];
//
//        FileInputStream fis = new FileInputStream(file);
//
//        outputStream.write(fis.readAllBytes());
//        outputStream.close();
//
//    }

    @GetMapping("/download-attachment/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = new FileSystemResource(attachmentsPath + "/" + filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
