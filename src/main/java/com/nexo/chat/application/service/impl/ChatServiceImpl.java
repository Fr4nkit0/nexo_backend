package com.nexo.chat.application.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import com.nexo.chat.application.dto.request.CreateChatRequest;
import com.nexo.chat.application.dto.response.ChatResponse;
import com.nexo.chat.application.mapper.ChatMapper;
import com.nexo.chat.application.service.ChatService;
import com.nexo.chat.domain.persistence.Chat;
import com.nexo.chat.domain.repository.ChatRepository;
import com.nexo.user.domain.persistence.User;
import com.nexo.user.domain.repository.UserRepository;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    public ChatServiceImpl(UserRepository userRepository, ChatRepository chatRepository, ChatMapper ChatMapper) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.chatMapper = ChatMapper;
    }

    @Transactional(readOnly = true)
    public List<ChatResponse> findAllChatsByReceiverId(Authentication currentUser) {
        String username = currentUser.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UUID userId = user.getId();
        return chatRepository.findChatsBySenderId(userId)
                .stream()
                .map(chat -> chatMapper.toChatResponse(chat, userId))
                .toList();
    }

    @Override
    public UUID createChat(CreateChatRequest createChatRequest) {
        Optional<Chat> existingChat = chatRepository.findChatByReceiverAndSender(createChatRequest.getSenderId(),
                createChatRequest.getReceiverId());
        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }
        User sender = userRepository.findByPublicId(createChatRequest.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with id " + createChatRequest.getSenderId() + " not found"));
        User receiver = userRepository.findByPublicId(createChatRequest.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with id " + createChatRequest.getReceiverId() + " not found"));

        Chat savedChat = chatRepository.save(Chat.builder()
                .sender(sender)
                .recipient(receiver)
                .build());
        return savedChat.getId();
    }

}
