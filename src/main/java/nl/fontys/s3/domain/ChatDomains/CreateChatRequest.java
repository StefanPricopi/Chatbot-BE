package nl.fontys.s3.domain.ChatDomains;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.domain.User;

@Data
@Builder
public class CreateChatRequest {
    // Might be a bit overkill tbh. But its only for creating a chat session.
    @NotNull
    private User user;
}
