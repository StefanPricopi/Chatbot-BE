package nl.fontys.s3.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ChatEntity {
    private long id;

    private long customer_id;
    private List<MessageEntity> messages;

    // Add date
    private LocalDateTime dateTime;
    //Maybe a status?
    //Maybe a priority?
    private boolean isOpen;
}
