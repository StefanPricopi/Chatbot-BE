package nl.fontys.s3.domain.ChatDomains;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.persistence.entity.MessageEntity;
import nl.fontys.s3.persistence.entity.UserEntity;

import java.util.List;

@Data
@Builder
public class ReadChatResponse {
    @NotNull
    private long id;
    @NotNull
    private UserEntity customer;
    @NotNull
    private List<MessageEntity> messages;
    @NotNull
    private boolean hasBeenSolved;
}
