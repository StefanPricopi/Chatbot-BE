package nl.fontys.s3.domain.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMailRequest {
    private String to;
    private String subject;
    private String body;
}
