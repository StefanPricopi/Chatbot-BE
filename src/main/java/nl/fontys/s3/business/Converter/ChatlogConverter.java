package nl.fontys.s3.business.Converter;

import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;
import nl.fontys.s3.domain.ChatDomains.SendByDTO;
import nl.fontys.s3.persistence.entity.ChatEntity;

public class ChatlogConverter {
    public static ReadChatResponse chatConverter(ChatEntity chatEntity)
    {
        SendByDTO temp = SendByDTO.builder()
                .userId(chatEntity.getCustomer().getUserid())
                .email(chatEntity.getCustomer().getEmail())
                .dateTime(chatEntity.getDateTime())
                .roles(chatEntity.getCustomer().getRolesSet())
                .build();


        return ReadChatResponse.builder()
                .id(chatEntity.getId())
                .createdBy(temp)
                .messages(chatEntity.getMessages())
                .hasBeenSolved(chatEntity.isOpen())
                .dateTime(chatEntity.getDateTime())
                .build();
    }
}
