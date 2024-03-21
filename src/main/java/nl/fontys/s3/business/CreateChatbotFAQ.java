package nl.fontys.s3.business;

import nl.fontys.s3.domain.CreateChatbotFAQRequest;
import nl.fontys.s3.domain.CreateChatbotFAQResponse;


public interface CreateChatbotFAQ {
    CreateChatbotFAQResponse createFAQ(CreateChatbotFAQRequest request);
}
