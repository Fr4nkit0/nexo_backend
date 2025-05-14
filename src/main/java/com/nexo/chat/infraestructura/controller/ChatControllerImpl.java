package com.nexo.chat.infraestructura.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexo.chat.application.dto.request.CreateChatRequest;
import com.nexo.chat.application.dto.response.ChatResponse;
import com.nexo.chat.application.service.ChatService;
import com.nexo.common.infraestructura.dto.StringResponse;

@RestController
@RequestMapping("/chats")
public class ChatControllerImpl implements ChatController {
    private final ChatService chatService;

    public ChatControllerImpl(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<StringResponse> createChat(@RequestBody CreateChatRequest createChatRequest) {
        final UUID chatId = chatService.createChat(createChatRequest);
        StringResponse response = StringResponse.builder()
                .response(chatId.toString())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> findChatsByReceiver(Authentication authentication) {
        return ResponseEntity.ok(chatService.findAllChatsByReceiverId(authentication));
    }

}
