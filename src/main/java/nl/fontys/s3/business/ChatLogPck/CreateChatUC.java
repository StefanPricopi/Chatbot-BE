package nl.fontys.s3.business.ChatLogPck;

import nl.fontys.s3.domain.ChatDomains.CreateChatResponse;

public interface CreateChatUC {
    CreateChatResponse createChat(long customerId);
}
