package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.RetrieveAllChatsUC;
import nl.fontys.s3.business.Converter.ChatlogConverter;
import nl.fontys.s3.domain.ChatDomains.GetAllChatsResponse;
import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;
import nl.fontys.s3.persistence.ChatlogRepoJPA;
import nl.fontys.s3.persistence.ChatlogRepositoryFAKE;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class RetrieveAllChatsUCImpl implements RetrieveAllChatsUC {

    private ChatlogRepoJPA chatlogRepositoryFAKE;

    @Override
    public GetAllChatsResponse getAllChats() {

        List<ReadChatResponse> tempChat = chatlogRepositoryFAKE.findAll()
                .stream()
                .map(ChatlogConverter::chatConverter)
                .toList();


        return GetAllChatsResponse.builder()
                .allChats(tempChat)
                .build();
    }
}
