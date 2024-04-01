package nl.fontys.s3.business.ChatLogPck;

import nl.fontys.s3.domain.ChatDomains.MessageRequest;

public interface LogChatUC {
    void logChat(long chatId, MessageRequest msg);
}
