package nl.fontys.s3.business.ChatLogPck;

import nl.fontys.s3.business.exceptions.UserNotFoundException;
import nl.fontys.s3.domain.ChatDomains.CreateChatResponse;
import nl.fontys.s3.domain.ChatDomains.MessageRequest;

public interface CreateChatUC {
    CreateChatResponse createChat(long customerId, MessageRequest messageRequest) throws UserNotFoundException;
}
