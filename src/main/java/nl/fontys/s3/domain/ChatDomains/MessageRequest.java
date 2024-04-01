package nl.fontys.s3.domain.ChatDomains;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageRequest {

    private long user_id;
    private String message;
}
