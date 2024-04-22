package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.LogChatUC;
import nl.fontys.s3.domain.ChatDomains.MessageRequest;
import nl.fontys.s3.persistence.ChatlogRepository;
import nl.fontys.s3.persistence.entity.MessageEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogChatUCImpl implements LogChatUC {

    private ChatlogRepository chatlogRepository;

    @Override
    public void logChat(long chat_id, MessageRequest msg) {

        chatlogRepository.logChat(chat_id, MessageEntity.builder()
                        .sendBy(msg.getSendBy())
                        .message(msg.getMessage())
                .build());
    }
}
