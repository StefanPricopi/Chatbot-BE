package nl.fontys.s3.business;

import nl.fontys.s3.domain.chatbotFAQ.CreateChatbotFAQRequest;
import nl.fontys.s3.domain.chatbotFAQ.CreateChatbotFAQResponse;


public interface CreateChatbotFAQ {
    CreateChatbotFAQResponse createFAQ(CreateChatbotFAQRequest request);
}
