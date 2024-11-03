package com.gamebuddy.chat.service;

import com.gamebuddy.chat.dto.MessageCreateDTO;
import com.gamebuddy.chat.dto.MessageViewDTO;
import com.gamebuddy.chat.exception.results.DataResult;
import com.gamebuddy.chat.exception.results.ErrorDataResult;
import com.gamebuddy.chat.exception.results.SuccessDataResult;
import com.gamebuddy.chat.model.Conversation;
import com.gamebuddy.chat.model.Message;
import com.gamebuddy.chat.repository.ConversationRepository;
import com.gamebuddy.chat.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private final ModelMapper modelMapper;

    public MessageService(MessageRepository messageRepository,
                          ConversationRepository conversationRepository,
                          SimpMessagingTemplate messagingTemplate, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.messagingTemplate = messagingTemplate;
        this.modelMapper = modelMapper;
    }

    public DataResult<MessageViewDTO> sendMessage(MessageCreateDTO messageCreateDTO) {
        // Konuşmayı kontrol et ve mesajı kaydet
        Conversation conversation = conversationRepository.findByUser1IdAndUser2Id(
                messageCreateDTO.getSenderId(), messageCreateDTO.getRecipientId());
        if (conversation == null) {
            return new ErrorDataResult<>("Firstly generate new conversation.");
        }

        Message message = new Message();
        message.setMessageId(UUID.randomUUID().toString());
        message.setTimestamp(LocalDateTime.now());
        message.setStatus("SENT");
        message.setContent(messageCreateDTO.getContent());
        messageRepository.save(message);

        MessageViewDTO messageViewDTO = new MessageViewDTO();
        messageViewDTO.setSenderId(messageCreateDTO.getSenderId());
        messageViewDTO.setContent(message.getContent());
        messageViewDTO.setTimestamp(message.getTimestamp());

        // WebSocket ile mesajı yayınla
        messagingTemplate.convertAndSend("/topic/public", messageViewDTO);

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
