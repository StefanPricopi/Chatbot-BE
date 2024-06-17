package nl.fontys.s3.domain.chatbotFAQ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.domain.ChatbotFAQ;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllChatbotFAQResponse {

        private List<ChatbotFAQ> chatbotFAQS;
}
