package nl.fontys.s3.persistence.entity;


import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.domain.ChatDomains.SendByDTO;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageEntity {
    private long message_id;
    private SendByDTO sendBy;
    private String message;
    private LocalDateTime dateTime;

}
