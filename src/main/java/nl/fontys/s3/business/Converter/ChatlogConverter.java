package nl.fontys.s3.business.Converter;

import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;
import nl.fontys.s3.persistence.entity.ChatEntity;

public class ChatlogConverter {
    public static ReadChatResponse chatConverter(ChatEntity chatEntity)
    {
        return ReadChatResponse.builder()
                .id(chatEntity.getId())
                .customer_id(chatEntity.getCustomer_id())
                .messages(chatEntity.getMessages())
                .hasBeenSolved(chatEntity.isOpen())
                .build();
    }
}
