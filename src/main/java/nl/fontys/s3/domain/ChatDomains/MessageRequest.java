package nl.fontys.s3.domain.ChatDomains;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageRequest {
    @NotNull
    private long user_id;
    @NotBlank
    private String message;
}
