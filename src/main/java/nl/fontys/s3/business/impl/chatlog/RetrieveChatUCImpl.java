package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.RetrieveChatUC;
import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;
import nl.fontys.s3.domain.ChatDomains.SendByDTO;
import nl.fontys.s3.persistence.ChatlogRepository;
import nl.fontys.s3.persistence.entity.ChatEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class RetrieveChatUCImpl implements RetrieveChatUC {

    private ChatlogRepository chatlogRepository;

    @Override
    public ReadChatResponse retrieveChat(long id) {

        Optional<ChatEntity> tmp = chatlogRepository.retrieveChat(id);
        SendByDTO tempSend = SendByDTO.builder()
                .userId(tmp.get().getCustomer().getUserId())
                .username(tmp.get().getCustomer().getUsername())
                .email(tmp.get().getCustomer().getEmail())
                .roles(tmp.get().getCustomer().getRoles())
                .build();

        return ReadChatResponse.builder()
                .id(tmp.get().getId())
                .createdBy(tempSend)
                .messages(tmp.get().getMessages())
                .dateTime(tmp.get().getDateTime())
                .hasBeenSolved(tmp.get().isOpen())
                .build();
    }
}
