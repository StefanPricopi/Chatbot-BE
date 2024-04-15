package nl.fontys.s3.business.impl;

import nl.fontys.s3.domain.ChatbotFAQ;
import nl.fontys.s3.domain.User;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import nl.fontys.s3.persistence.entity.UserEntity;

public class FAQConverter {

    public static ChatbotFAQ convert(ChatbotFAQEntity faq) {
        return ChatbotFAQ.builder()
                .FAQID(faq.getFAQID())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .category(faq.getCategory())
                .dateAdded(faq.getDateAdded())
                .user(convertUser(faq.getUser())) // Convert User object
                .build();
    }

    private static User convertUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return User.builder()
                .userId(userEntity.getUserId())
                .build();
    }
}
