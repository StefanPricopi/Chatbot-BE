package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.RetrieveChatUC;
import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;
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
        return ReadChatResponse.builder()
                .id(tmp.get().getId())
                .customer(tmp.get().getCustomer())
                .messages(tmp.get().getMessages())
                .hasBeenSolved(tmp.get().isOpen())
                .build();
    }
}
