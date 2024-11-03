package com.gamebuddy.chat.controller;

import com.gamebuddy.chat.dto.MessageCreateDTO;
import com.gamebuddy.chat.dto.MessageViewDTO;
import com.gamebuddy.chat.exception.results.DataResult;
import com.gamebuddy.chat.model.Message;
import com.gamebuddy.chat.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    private final ModelMapper modelMapper;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService, ModelMapper modelMapper) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @MessageMapping("/sendMessage") // İstemciden gelen mesajları dinle
    @SendTo("/topic/messages") // Mesajların gönderileceği hedef
    public Message sendMessage(MessageCreateDTO messageCreateDTO) {

        DataResult<MessageViewDTO> result = messageService.sendMessage(messageCreateDTO);

        return modelMapper.map(result.getData(), Message.class);
    }
}