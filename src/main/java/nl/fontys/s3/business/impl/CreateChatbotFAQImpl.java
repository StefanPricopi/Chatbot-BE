package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.CreateChatbotFAQ;
import nl.fontys.s3.domain.CreateChatbotFAQRequest;
import nl.fontys.s3.domain.CreateChatbotFAQResponse;
import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateChatbotFAQImpl implements CreateChatbotFAQ {
    //@Autowired
    @Qualifier("chatbotFAQRepositoryImpl")
    private ChatbotFAQJpaRepository chatbotFAQRepository;



    @Override
    public CreateChatbotFAQResponse createFAQ(CreateChatbotFAQRequest request) {
        ChatbotFAQEntity savedFAQ = saveNewFAQ(request);

        return CreateChatbotFAQResponse.builder()
                .FAQID((long) savedFAQ.getFAQID())
                .build();
    }

    private ChatbotFAQEntity saveNewFAQ(CreateChatbotFAQRequest request){
        ChatbotFAQEntity newFAQ = ChatbotFAQEntity.builder()
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .category(request.getCategory())
                .build();
        return chatbotFAQRepository.save(newFAQ);
    }
}