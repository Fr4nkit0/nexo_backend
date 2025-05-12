package com.nexo.file.application.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveFile(MultipartFile sourceFile, String userId);
}
