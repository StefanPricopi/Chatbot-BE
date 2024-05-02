package nl.fontys.s3.domain.ChatDomains;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.persistence.entity.UserEntity;

@Data
@Builder
public class MessageRequest {
    @NotNull
    private SendByDTO sendBy;
    @NotBlank
    private String message;
}
