package nl.fontys.s3.business.Converter;

import nl.fontys.s3.domain.ChatDomains.MessagesDTO;
import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;
import nl.fontys.s3.domain.ChatDomains.SendByDTO;
import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;

import java.util.ArrayList;
import java.util.List;

public class ChatlogConverter {
    public static ReadChatResponse chatConverter(ChatEntity chatEntity)
    {
        SendByDTO temp = SendByDTO.builder()
                .userId(chatEntity.getCustomer().getUserid())
                .email(chatEntity.getCustomer().getEmail())
                .dateTime(chatEntity.getDateTime())
                .roles(chatEntity.getCustomer().getRolesSet())
                .build();

        List<MessagesDTO> messagesDTOList = new ArrayList<>();

        for (MessageEntity x: chatEntity.getMessages())
        {
            SendByDTO tmpSendBy = SendByDTO.builder()
                    .username(x.getSendBy().getUsername())
                    .userId(x.getSendBy().getUserid())
                    .roles(x.getSendBy().getRolesSet())
                    .dateTime(x.getDateTime())
                    .email(x.getSendBy().getEmail())
                    .build();

            MessagesDTO msgDto = MessagesDTO.builder()
                    .sendByDTO(tmpSendBy)
                    .message(x.getMessage())
                    .dateTime(x.getDateTime())
                    .id(x.getId())
                    .build();

            messagesDTOList.add(msgDto);
        }


        return ReadChatResponse.builder()
                .id(chatEntity.getId())
                .createdBy(temp)
                .messages(messagesDTOList)
                .hasBeenSolved(chatEntity.isOpen())
                .dateTime(chatEntity.getDateTime())
                .build();
    }
}
