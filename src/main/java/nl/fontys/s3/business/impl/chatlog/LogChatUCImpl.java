package nl.fontys.s3.business.impl.chatlog;

import nl.fontys.s3.business.ChatLogPck.LogChatUC;
import nl.fontys.s3.domain.MessageRequest;
import nl.fontys.s3.persistence.ChatlogRepository;
import org.springframework.stereotype.Service;

@Service
public class LogChatUCImpl implements LogChatUC {

    private ChatlogRepository chatlogRepository;

    @Override
    public void logChat(long id, MessageRequest msg) {

    }
}
