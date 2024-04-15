package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.RetrieveAllChatsUC;
import nl.fontys.s3.business.ChatLogPck.RetrieveChatUC;
import nl.fontys.s3.business.Converter.ChatlogConverter;
import nl.fontys.s3.domain.ChatDomains.GetAllChatsResponse;
import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;
import nl.fontys.s3.persistence.ChatlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class RetrieveAllChatsUCImpl implements RetrieveAllChatsUC {

    private ChatlogRepository chatlogRepository;

    @Override
    public GetAllChatsResponse getAllChats() {

        List<ReadChatResponse> tempChat = chatlogRepository.retrieveAllChats()
                .stream()
                .map(ChatlogConverter::chatConverter)
                .toList();

        return GetAllChatsResponse.builder()
                .allChats(tempChat)
                .build();
    }
}
