package nl.fontys.s3.domain.ChatDomains;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageRequest {
    @NotNull
    private long user_id;
    @NotBlank
    private String message;
    private LocalDateTime dateTime;
}
