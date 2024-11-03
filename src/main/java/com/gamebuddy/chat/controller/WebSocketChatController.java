package com.gamebuddy.chat.controller;

import com.gamebuddy.chat.dto.MessageCreateDTO;
import com.gamebuddy.chat.dto.MessageViewDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class WebSocketChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageViewDTO sendMessage(MessageCreateDTO messageCreateDTO) {
        MessageViewDTO message = new MessageViewDTO();
        message.setSenderId(messageCreateDTO.getSenderId());
        message.setContent(messageCreateDTO.getContent());
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public MessageViewDTO addUser(MessageCreateDTO messageCreateDTO) {
        // Yeni kullanıcı mesajı için
        MessageViewDTO welcomeMessage = new MessageViewDTO();
        welcomeMessage.setContent("User joined: " + messageCreateDTO.getSenderId());
        welcomeMessage.setTimestamp(LocalDateTime.now());
        return welcomeMessage;
    }
}
