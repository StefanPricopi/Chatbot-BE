package nl.fontys.s3.domain.ChatDomains;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.persistence.entity.MessageEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReadChatResponse {
    @NotNull
    private long id;
    @NotNull
    private SendByDTO createdBy;
    @NotNull
    private List<MessageEntity> messages;

    private LocalDateTime dateTime;

    @NotNull
    private boolean hasBeenSolved;
}
