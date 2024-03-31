package nl.fontys.s3.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatEntity {
    private long id;

    private long customer_id;
    private List<MessageEntity> messages;

    //Maybe a status?
    //Maybe a priority?
}
