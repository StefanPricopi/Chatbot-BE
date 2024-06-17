package nl.fontys.s3.domain.ChatDomains;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateChatStatusRequest {
    long chatId;
    boolean status;
}
