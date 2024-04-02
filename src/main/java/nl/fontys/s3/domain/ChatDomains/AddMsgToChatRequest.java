package nl.fontys.s3.domain.ChatDomains;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.persistence.entity.MessageEntity;

@Data
@Builder
public class AddMsgToChatRequest {
    // Add chatmessage to log.
    @NotNull
    private int chat_id;
    @NotNull
    private MessageRequest message;

}
