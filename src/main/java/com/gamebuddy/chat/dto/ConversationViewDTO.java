package com.gamebuddy.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversationViewDTO {
    private String conversationId;
    private String user1Id;
    private String user2Id;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
}
