package com.nexo.file.application.service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveFile(MultipartFile sourceFile, UUID userId);
}
