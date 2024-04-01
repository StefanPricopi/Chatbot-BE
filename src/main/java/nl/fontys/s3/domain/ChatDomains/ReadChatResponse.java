package nl.fontys.s3.domain.ChatDomains;

import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.persistence.entity.MessageEntity;

import java.util.List;

@Data
@Builder
public class ReadChatResponse {
    private long id;

    private long customer_id;
    private List<MessageEntity> messages;
}
