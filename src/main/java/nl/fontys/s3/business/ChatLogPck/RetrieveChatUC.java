package nl.fontys.s3.business.ChatLogPck;

import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;

public interface RetrieveChatUC {
    ReadChatResponse retrieveChat(long id);
}
