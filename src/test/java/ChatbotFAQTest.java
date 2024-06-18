import nl.fontys.s3.business.impl.*;
import nl.fontys.s3.domain.*;
import nl.fontys.s3.domain.chatbotFAQ.CreateChatbotFAQRequest;
import nl.fontys.s3.domain.chatbotFAQ.CreateChatbotFAQResponse;
import nl.fontys.s3.domain.chatbotFAQ.GetAllChatbotFAQResponse;
import nl.fontys.s3.domain.chatbotFAQ.UpdateChatbotFAQRequest;
import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatbotFAQTest {

    @Mock
    private ChatbotFAQJpaRepository chatbotFAQRepository;
    @Mock
    private BidService bidService;
    @Mock
    private KeywordMapGenerator keywordMapGenerator;
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

    @Test
    public void testProcessUserQuery_BidRelated() {
        String userInput = "What is my bid status?";
        int userId = 123;
        int attempts = 0;
        boolean endOfConversation = false;

        when(bidService.getAnswerForBidQuestion(anyString(), anyInt())).thenReturn("Your bid status is active.");

        String response = getChatbotFAQ.processUserQuery(userInput, userId, attempts, endOfConversation);

        assertEquals("Your bid status is active.", response);
    }

    @Test
    public void testProcessUserQuery_FAQFound() {
        String userInput = "How to reset my password?";
        int userId = 123;
        int attempts = 0;
        boolean endOfConversation = false;

        when(chatbotFAQRepository.findAll()).thenReturn(createFAQs());
        when(keywordMapGenerator.extractKeywords(anyString())).thenReturn(Arrays.asList("reset", "password"));

        String response = getChatbotFAQ.processUserQuery(userInput, userId, attempts, endOfConversation);

        assertEquals("You can reset your password by clicking 'Forgot Password' on the login page.", response);
    }

    @Test
    public void testProcessUserQuery_EndOfConversation() {
        String userInput = "How to reset my password?";
        int userId = 123;
        int attempts = 0;
        boolean endOfConversation = true;

        when(chatbotFAQRepository.findAll()).thenReturn(createFAQs());
        when(keywordMapGenerator.extractKeywords(anyString())).thenReturn(Arrays.asList("reset", "password"));

        String response = getChatbotFAQ.processUserQuery(userInput, userId, attempts, endOfConversation);

        assertEquals("You can reset your password by clicking 'Forgot Password' on the login page.\n\nWas this helpful? [YES/NO]", response);
    }
    @Test
    public void testCalculateBestMatchingFAQ_MatchFound() {
        when(chatbotFAQRepository.findAll()).thenReturn(createFAQs());
        when(keywordMapGenerator.extractKeywords(anyString())).thenReturn(Arrays.asList("reset", "password"));

        String result = getChatbotFAQ.calculateBestMatchingFAQ("How to reset my password?");

        assertEquals("You can reset your password by clicking 'Forgot Password' on the login page.", result);
    }


    private List<ChatbotFAQEntity> createFAQs() {
        ChatbotFAQEntity faq1 = new ChatbotFAQEntity();
        faq1.setQuestion("How to reset my password?");
        faq1.setAnswer("You can reset your password by clicking 'Forgot Password' on the login page.");
        return Arrays.asList(faq1);
    }
}
