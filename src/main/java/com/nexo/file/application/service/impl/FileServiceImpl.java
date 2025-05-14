package com.nexo.file.application.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.nexo.file.application.service.FileService;
import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Service
public class FileServiceImpl implements FileService {

    private final String fileUploadPath;

    public FileServiceImpl(@Value("${application.file.uploads.media-output-path}") String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    @Override
    public String saveFile(MultipartFile sourceFile, UUID userId) {
        final String fileUploadSubPath = "users" + separator + userId.toString();
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    private String uploadFile(
            @Nonnull MultipartFile sourceFile,
            @Nonnull String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);

        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                return null;
            }
        }
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = finalUploadPath + separator + currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            return targetFilePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

}
