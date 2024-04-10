package nl.fontys.s3.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatbotFAQ {

        private Long FAQID;
        private String question;
        private String answer;
        private String category;

}
