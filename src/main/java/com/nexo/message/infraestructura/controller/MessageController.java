package com.nexo.message.infraestructura.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.nexo.message.application.dto.request.MessageRequest;
import com.nexo.message.application.dto.response.MessageResponse;

public interface MessageController {

    void saveMessage(@RequestBody MessageRequest message);

    void uploadMedia(
            @RequestParam("chat-id") UUID chatId,
            @RequestPart("file") MultipartFile file,
            Authentication authentication);

    void setMessageToSeen(
            @RequestParam("chat-id") UUID chatId,
            Authentication authentication);

    ResponseEntity<List<MessageResponse>> getAllMessages(
            @PathVariable("chat-id") UUID chatId);
}
