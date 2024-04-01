package nl.fontys.s3.domain.ChatDomains;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateChatRequest {
    // Might be a bit overkill tbh. But its only for creating a chat session.
    private int user_id;
}
