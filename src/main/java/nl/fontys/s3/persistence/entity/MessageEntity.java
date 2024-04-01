package nl.fontys.s3.persistence.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageEntity {
    private long message_id;
    private long user_id;
    private String message;
}
