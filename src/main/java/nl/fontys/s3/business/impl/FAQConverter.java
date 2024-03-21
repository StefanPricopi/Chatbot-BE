package nl.fontys.s3.business.impl;

import nl.fontys.s3.domain.ChatbotFAQ;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;

public class FAQConverter {

        public static ChatbotFAQ convert(ChatbotFAQEntity faq) {
            return ChatbotFAQ.builder()
                    .FAQID(faq.getFAQID())
                    .question(faq.getQuestion())
                    .answer(faq.getAnswer())
                    .category(faq.getCategory())
                    .dateAdded(faq.getDateAdded())
                    .build();
        }

}
