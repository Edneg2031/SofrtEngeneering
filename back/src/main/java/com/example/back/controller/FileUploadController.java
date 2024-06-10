package com.example.back.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.util.StringUtils;

@RestController
@CrossOrigin
public class FileUploadController {

    @PostMapping("/upload/avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件为空");
        }

        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileName = userId + "." + fileExtension;  // 使用用户ID作为文件名
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

        try {
            File uploadFile = new File(uploadDir + fileName);
            file.transferTo(uploadFile);
            return ResponseEntity.ok("/uploads/" + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("文件上传失败: " + e.getMessage());
        }
    }
    private final Path avatarLocation = Paths.get("src/main/resources/static/uploads");

    @GetMapping("/user/avatar/{userId}")
    public ResponseEntity<Resource> getUserAvatar(@PathVariable Long userId) {
        try {
            Path file = avatarLocation.resolve(userId + ".jpg");
            if (!Files.exists(file)) {
                file = avatarLocation.resolve(userId + ".png");
            }
            if (!Files.exists(file)) {
                return ResponseEntity.notFound().build();
            }
            Resource resource = new UrlResource(file.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
