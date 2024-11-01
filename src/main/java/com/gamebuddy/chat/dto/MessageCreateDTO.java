package com.gamebuddy.chat.dto;

import lombok.Data;

@Data
public class MessageCreateDTO {
    private String conversationId;
    private String senderId;
    private String recipientId;
    private String content;
}
