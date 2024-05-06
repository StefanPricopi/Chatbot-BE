package nl.fontys.s3.domain.LivechatDomains;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {
    private int chatId;
    private String message;
}
