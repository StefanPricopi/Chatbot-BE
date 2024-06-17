package nl.fontys.s3.business;

import nl.fontys.s3.domain.chatbotFAQ.GetAllChatbotFAQResponse;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;

import java.util.List;
import java.util.Map;

public interface GetChatbotFAQ {
    GetAllChatbotFAQResponse getFAQ();
    List<ChatbotFAQEntity> getFAQsByKeyword(String keyword);
    String processUserQuery(String userInput, int userId, int attempts, boolean state);
    Map<String, List<String>> getKeywordMap();


}
