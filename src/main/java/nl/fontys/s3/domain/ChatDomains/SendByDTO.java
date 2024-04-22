package nl.fontys.s3.domain.ChatDomains;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SendByDTO {
    private Long userId;
    private String userName;
    private String email;
    private String role;
}
