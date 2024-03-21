package nl.fontys.s3.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChatbotFAQ {

        private int FAQID;
        private String question;
        private String answer;
        private String category;
        private Timestamp dateAdded;

}
