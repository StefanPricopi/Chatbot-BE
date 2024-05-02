package nl.fontys.s3.business.Converter;

import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;
import nl.fontys.s3.domain.ChatDomains.SendByDTO;
import nl.fontys.s3.persistence.entity.ChatEntity;

public class ChatlogConverter {
    public static ReadChatResponse chatConverter(ChatEntity chatEntity)
    {
        SendByDTO temp = SendByDTO.builder()
                .userId(chatEntity.getCustomer().getUserId())
                .userName(chatEntity.getCustomer().getUserName())
                .email(chatEntity.getCustomer().getEmail())
                .role(chatEntity.getCustomer().getRole())
                .build();


        return ReadChatResponse.builder()
                .id(chatEntity.getId())
                .sendBy(temp)
                .messages(chatEntity.getMessages())
                .hasBeenSolved(chatEntity.isOpen())
                .build();
    }
}
