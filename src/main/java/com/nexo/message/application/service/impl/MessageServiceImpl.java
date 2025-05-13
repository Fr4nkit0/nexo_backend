package com.nexo.message.application.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nexo.chat.domain.persistence.Chat;
import com.nexo.chat.domain.repository.ChatRepository;
import com.nexo.file.application.service.FileService;
import com.nexo.file.application.util.FileUtils;
import com.nexo.message.application.dto.request.MessageRequest;
import com.nexo.message.application.dto.response.MessageResponse;
import com.nexo.message.application.mapper.MessageMapper;
import com.nexo.message.application.service.MessageService;
import com.nexo.message.domain.persistence.Message;
import com.nexo.message.domain.repository.MessageRepository;
import com.nexo.message.domain.util.MessageState;
import com.nexo.message.domain.util.MessageType;
import com.nexo.notification.application.service.NotificationService;
import com.nexo.notification.domain.model.Notification;
import com.nexo.notification.domain.util.NotificationType;
import com.nexo.user.domain.persistence.User;
import com.nexo.user.domain.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final NotificationService notificationService;
    private final FileService fileService;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository,
            ChatRepository chatRepository, NotificationService notificationService, FileService fileService,
            MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.notificationService = notificationService;
        this.fileService = fileService;
        this.messageMapper = messageMapper;
    }

    @Override
    public void saveMessage(MessageRequest messageRequest) {
        Chat chat = findChatById(messageRequest.getChatId());
        Message message = messageRepository.save(messageMapper.toMessage(messageRequest, chat));
        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .messageType(messageRequest.getType())
                .content(messageRequest.getContent())
                .senderId(messageRequest.getSenderId())
                .receiverId(messageRequest.getReceiverId())
                .type(NotificationType.MESSAGE)
                .chatName(chat.getTargetChatName(message.getSenderId()))
                .build();
        notificationService.sendNotification(messageRequest.getReceiverId(), notification);
    }

    @Override
    public void setMessagesToSeen(UUID chatId, Authentication authentication) {
        Chat chat = findChatById(chatId);
        final UUID recipientId = getRecipientId(chat, authentication);
        messageRepository.setMessagesToSeenByChatId(chatId, MessageState.SEEN);
        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .type(NotificationType.SEEN)
                .receiverId(recipientId)
                .senderId(getSenderId(chat, authentication))
                .build();
        notificationService.sendNotification(recipientId, notification);

    }

    @Override
    public void uploadMediaMessage(UUID chatId, MultipartFile file, Authentication authentication) {
        Chat chat = findChatById(chatId);
        final UUID senderId = getSenderId(chat, authentication);
        final UUID receiverId = getRecipientId(chat, authentication);
        final String filePath = fileService.saveFile(file, senderId);
        Message message = Message.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .state(MessageState.SENT)
                .type(MessageType.IMAGE)
                .mediaFilePath(filePath)
                .chat(chat)
                .build();
        messageRepository.save(message);
        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .type(NotificationType.IMAGE)
                .senderId(senderId)
                .receiverId(receiverId)
                .messageType(MessageType.IMAGE)
                .media(FileUtils.readFileFromLocation(filePath))
                .build();
        notificationService.sendNotification(receiverId, notification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageResponse> findChatMessages(UUID chatId) {
        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(messageMapper::toMessageResponse)
                .toList();
    }

    private UUID getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(getIdUserAuthentication(authentication))) {
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }

    private UUID getRecipientId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(getIdUserAuthentication(authentication))) {
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }

    private UUID getIdUserAuthentication(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }

    private Chat findChatById(UUID chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        return chat;
    }
}
