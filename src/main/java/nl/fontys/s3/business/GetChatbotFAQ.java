package nl.fontys.s3.business;

import nl.fontys.s3.domain.GetAllChatbotFAQResponse;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;

import java.util.List;

public interface GetChatbotFAQ {
    GetAllChatbotFAQResponse getFAQ();
    List<ChatbotFAQEntity> getFAQsByKeyword(String keyword);
    String processUserQuery(String userInput, int userId, int attempts, boolean state);
}
