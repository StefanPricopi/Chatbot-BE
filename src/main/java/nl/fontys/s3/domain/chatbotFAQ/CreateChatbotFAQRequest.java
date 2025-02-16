package nl.fontys.s3.domain.chatbotFAQ;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatbotFAQRequest {
    @NotBlank
    private String question;
    @NotBlank
    private String answer;
    @NotBlank
    private String category;
}
