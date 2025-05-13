package com.nexo.message.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nexo.message.domain.persistence.Message;
import com.nexo.message.domain.util.MessageState;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.chat.id = ?1 ORDER BY m.createdDate")
    List<Message> findMessagesByChatId(UUID chatId);

    @Query("UPDATE Message SET state = ?2 WHERE chat.id = ?1")
    @Modifying
    void setMessagesToSeenByChatId(UUID chatId, MessageState state);

}
