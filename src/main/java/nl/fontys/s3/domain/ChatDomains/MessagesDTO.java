package nl.fontys.s3.domain.ChatDomains;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MessagesDTO {
    private long id;
    private String message;
    private LocalDateTime dateTime;
    private SendByDTO sendByDTO;
}
