package com.gamebuddy.chat.repository;

import com.gamebuddy.chat.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {
    List<Conversation> findByUser1IdOrUser2Id(String user1Id, String user2Id);
}
