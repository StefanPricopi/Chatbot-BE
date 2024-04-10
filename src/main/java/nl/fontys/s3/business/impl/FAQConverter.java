package nl.fontys.s3.business.impl;

import lombok.NoArgsConstructor;
import nl.fontys.s3.domain.ChatbotFAQ;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;

@NoArgsConstructor
public class FAQConverter {

        public static ChatbotFAQ convert(ChatbotFAQEntity faq) {
            return ChatbotFAQ.builder()
                    .FAQID(faq.getFAQID())
                    .question(faq.getQuestion())
                    .answer(faq.getAnswer())
                    .category(faq.getCategory())
                    .build();
        }

}
