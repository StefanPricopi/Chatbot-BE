import nl.fontys.s3.business.CreateChatbotFAQ;
import nl.fontys.s3.business.impl.CreateChatbotFAQImpl;
import nl.fontys.s3.business.impl.DeleteChatbotFAQImpl;
import nl.fontys.s3.business.impl.GetChatbotFAQImpl;
import nl.fontys.s3.business.impl.UpdateChatbotFAQImpl;
import nl.fontys.s3.domain.*;
import nl.fontys.s3.persistence.entity.UserEntity;
import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ChatbotFAQTest {

    @Mock
    private ChatbotFAQJpaRepository chatbotFAQRepository;

    @InjectMocks
    private CreateChatbotFAQImpl createChatbotFAQ;
    @InjectMocks
    private DeleteChatbotFAQImpl deleteChatbotFAQ;
    @InjectMocks
    private GetChatbotFAQImpl getChatbotFAQ;
    @InjectMocks
    private UpdateChatbotFAQImpl updateChatbotFAQ;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createFAQ_Success() {
        // Given
        CreateChatbotFAQRequest request = new CreateChatbotFAQRequest("What is AI?", "AI is...", "General");

        ChatbotFAQEntity savedFAQ = ChatbotFAQEntity.builder()
                .FAQID(1)
                .question("What is AI?")
                .answer("AI is...")
                .category("General")
                .build();

        when(chatbotFAQRepository.save(any(ChatbotFAQEntity.class))).thenReturn(savedFAQ);

        // When
        CreateChatbotFAQResponse response = createChatbotFAQ.createFAQ(request);

        // Then
        assertEquals(1, response.getFAQID());
        verify(chatbotFAQRepository, times(1)).save(any(ChatbotFAQEntity.class));
    }
    @Test
    void createFAQ_SaveNewFAQ_Success() {
        // Given
        CreateChatbotFAQRequest request = new CreateChatbotFAQRequest("What is AI?", "AI is...", "General");

        ChatbotFAQEntity savedFAQ = ChatbotFAQEntity.builder()
                .FAQID(1)
                .question("What is AI?")
                .answer("AI is...")
                .category("General")
                .build();

        when(chatbotFAQRepository.save(any(ChatbotFAQEntity.class))).thenReturn(savedFAQ);

        // When
        CreateChatbotFAQResponse response = createChatbotFAQ.createFAQ(request);

        // Then
        assertEquals(savedFAQ.getFAQID(), response.getFAQID());
        verify(chatbotFAQRepository, times(1)).save(any(ChatbotFAQEntity.class));
    }
    @Test
    void createFAQ_SaveNewFAQ_Failure() {
        // Given
        CreateChatbotFAQRequest request = new CreateChatbotFAQRequest("What is AI?", "AI is...", "General");

        when(chatbotFAQRepository.save(any(ChatbotFAQEntity.class))).thenThrow(RuntimeException.class);

        // When
        assertThrows(RuntimeException.class, () -> createChatbotFAQ.createFAQ(request));

        // Then
        verify(chatbotFAQRepository, times(1)).save(any(ChatbotFAQEntity.class));
    }
    @Test
    void deleteFAQ_HappyPath() {
        // Given
        int FAQId = 1;

        // When
        deleteChatbotFAQ.deleteFAQ(FAQId);

        // Then
        verify(chatbotFAQRepository, times(1)).deleteById(FAQId);
    }

    @Test
    void deleteFAQ_AlternativePath() {
        // Given
        int FAQId = 1;
        doThrow(RuntimeException.class).when(chatbotFAQRepository).deleteById(FAQId);

        // When
        // Then
        assertThrows(RuntimeException.class, () -> deleteChatbotFAQ.deleteFAQ(FAQId));
        verify(chatbotFAQRepository, times(1)).deleteById(FAQId);
    }
    @Test
    void getFAQ() {
        // Given
        List<ChatbotFAQEntity> faqEntities = Collections.singletonList(
                new ChatbotFAQEntity("What is AI?", "AI is...", "General", null)
        );
        when(chatbotFAQRepository.findAll()).thenReturn(faqEntities);

        // When
        GetAllChatbotFAQResponse response = getChatbotFAQ.getFAQ();

        // Then
        assertEquals(1, response.getChatbotFAQS().size());
        ChatbotFAQ faq = response.getChatbotFAQS().get(0);
        assertEquals("What is AI?", faq.getQuestion());
        assertEquals("AI is...", faq.getAnswer());
        assertEquals("General", faq.getCategory());
    }

    @Test
    void getFAQsByKeyword() {
        // Given
        String keyword = "AI";
        List<ChatbotFAQEntity> faqEntities = Collections.singletonList(
                new ChatbotFAQEntity("What is AI?", "AI is...", "General", null)
        );
        when(chatbotFAQRepository.findByQuestionContainingIgnoreCase(keyword)).thenReturn(faqEntities);

        // When
        List<ChatbotFAQEntity> result = getChatbotFAQ.getFAQsByKeyword(keyword);

        // Then
        assertEquals(1, result.size());
        assertEquals("What is AI?", result.get(0).getQuestion());
        assertEquals("AI is...", result.get(0).getAnswer());
        assertEquals("General", result.get(0).getCategory());
    }
    @Test
    void updateChatbotFAQ_Found() {
        // Given
        UpdateChatbotFAQRequest request = new UpdateChatbotFAQRequest(1L, "Updated question", "Updated answer", "Updated category");
        ChatbotFAQEntity existingFAQ = new ChatbotFAQEntity("Original question", "Original answer", "Original category", null);
        Optional<ChatbotFAQEntity> faqOptional = Optional.of(existingFAQ);

        when(chatbotFAQRepository.findById(Math.toIntExact(request.getFAQID()))).thenReturn(faqOptional);

        // When
        updateChatbotFAQ.updateChatbotFAQ(request);

        // Then
        verify(chatbotFAQRepository, times(1)).findById(Math.toIntExact(request.getFAQID()));
        verify(chatbotFAQRepository, times(1)).save(existingFAQ);
        assertEquals("Updated question", existingFAQ.getQuestion());
        assertEquals("Updated answer", existingFAQ.getAnswer());
        assertEquals("Updated category", existingFAQ.getCategory());
    }

    @Test
    void updateChatbotFAQ_NotFound() {
        // Given
        UpdateChatbotFAQRequest request = new UpdateChatbotFAQRequest(1L, "Updated question", "Updated answer", "Updated category");
        Optional<ChatbotFAQEntity> faqOptional = Optional.empty();

        when(chatbotFAQRepository.findById(Math.toIntExact(request.getFAQID()))).thenReturn(faqOptional);

        // When
        updateChatbotFAQ.updateChatbotFAQ(request);

        // Then
        verify(chatbotFAQRepository, times(1)).findById(Math.toIntExact(request.getFAQID()));
        verify(chatbotFAQRepository, never()).save(any());
    }
}
