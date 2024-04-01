package nl.fontys.s3.domain.ChatDomains;

import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.persistence.entity.MessageEntity;

@Data
@Builder
public class AddMsgToChatRequest {
    // Add chatmessage to log.

    private int chat_id;

    private MessageRequest message;

}
