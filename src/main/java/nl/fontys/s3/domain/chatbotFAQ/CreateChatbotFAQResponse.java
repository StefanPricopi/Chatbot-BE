package nl.fontys.s3.domain.chatbotFAQ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatbotFAQResponse {
    private Long FAQID;
}
