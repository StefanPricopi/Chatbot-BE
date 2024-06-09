package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.RetrieveChatUC;
import nl.fontys.s3.domain.ChatDomains.MessagesDTO;
import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;
import nl.fontys.s3.domain.ChatDomains.SendByDTO;
import nl.fontys.s3.persistence.ChatlogRepoJPA;
import nl.fontys.s3.persistence.ChatlogRepositoryFAKE;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RetrieveChatUCImpl implements RetrieveChatUC {

    private ChatlogRepoJPA chatlogRepoJPA;
    private UserRepository userRepository;

    @Override
    public ReadChatResponse retrieveChat(long id) {

        Optional<ChatEntity> tmp = chatlogRepoJPA.findById(id);

        SendByDTO chatsendBy = SendByDTO.builder()
                .userId(tmp.get().getCustomer().getUserid())
                .username(tmp.get().getCustomer().getUsername())
                .roles(tmp.get().getCustomer().getRolesSet())
                .email(tmp.get().getCustomer().getEmail())
                .dateTime(tmp.get().getDateTime())
                .build();

        List<MessagesDTO> messagesDTOList = new ArrayList<>();

        for(MessageEntity msg : tmp.get().getMessages())
        {
            SendByDTO sendByDTO = SendByDTO.builder()
                    .username(msg.getSendBy().getUsername())
                    .userId(msg.getSendBy().getUserid())
                    .email(msg.getSendBy().getEmail())
                    .roles(msg.getSendBy().getRolesSet())
                    .dateTime(msg.getDateTime())
                    .build();


            MessagesDTO msgDTO = MessagesDTO.builder()
                    .sendByDTO(sendByDTO)
                    .message(msg.getMessage())
                    .id(msg.getId())
                    .dateTime(msg.getDateTime())
                    .build();

            messagesDTOList.add(msgDTO);

        }




        return ReadChatResponse.builder()
                .id(tmp.get().getId())
                .createdBy(chatsendBy)
                .messages(messagesDTOList)
                .dateTime(tmp.get().getDateTime())
                .hasBeenSolved(tmp.get().isOpen())
                .build();
    }
}
