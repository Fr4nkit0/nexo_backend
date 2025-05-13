package com.nexo.chat.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nexo.chat.domain.persistence.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query("SELECT DISTINCT c FROM Chat c WHERE c.sender.id =?1 OR c.recipient.id = ?1 ORDER BY createdDate DESC")
    List<Chat> findChatsBySenderId(UUID senderId);

    @Query("SELECT DISTINCT c FROM Chat c WHERE (c.sender.id = ?1 AND c.recipient.id = ?2) OR (c.sender.id = ?2 AND c.recipient.id = ?1) ORDER BY createdDate DESC")
    Optional<Chat> findChatByReceiverAndSender(UUID senderId, UUID recipientId);

}
