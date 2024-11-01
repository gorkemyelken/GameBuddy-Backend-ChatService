package com.gamebuddy.chat.service;

import com.gamebuddy.chat.dto.MessageCreateDTO;
import com.gamebuddy.chat.dto.MessageViewDTO;
import com.gamebuddy.chat.exception.results.DataResult;
import com.gamebuddy.chat.exception.results.SuccessDataResult;
import com.gamebuddy.chat.model.Message;
import com.gamebuddy.chat.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    private final MessageRepository messageRepository;

    private final ModelMapper modelMapper;

    public MessageService(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    public DataResult<MessageViewDTO> sendMessage(MessageCreateDTO messageCreateDTO) {
        Message message = modelMapper.map(messageCreateDTO, Message.class);
        message.setMessageId(UUID.randomUUID().toString());
        message.setTimestamp(LocalDateTime.now());
        message.setStatus("SENT");
        messageRepository.save(message);

        MessageViewDTO messageViewDTO = modelMapper.map(message, MessageViewDTO.class);
        return new SuccessDataResult<>(messageViewDTO, "Message sent successfully.");
    }

    public DataResult<List<MessageViewDTO>> getMessagesByConversation(String  conversationId) {
        List<Message> messages = messageRepository.findByConversationId(conversationId);
        List<MessageViewDTO> messageViewDTOs = messages.stream()
                .map(message -> modelMapper.map(message, MessageViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(messageViewDTOs, "Messages found successfully.");
    }
}
