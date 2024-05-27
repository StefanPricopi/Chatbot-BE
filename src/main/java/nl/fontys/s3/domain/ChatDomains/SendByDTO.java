package nl.fontys.s3.domain.ChatDomains;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
public class SendByDTO {
    private Long userId;
    private String username;
    private String email;
    private Set<String> roles;
    private LocalDateTime dateTime;
}
