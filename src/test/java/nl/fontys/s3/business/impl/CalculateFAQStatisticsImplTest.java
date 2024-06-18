package nl.fontys.s3.business.impl;

import nl.fontys.s3.business.GetChatbotFAQ;
import nl.fontys.s3.domain.FAQStatistics;
import nl.fontys.s3.persistence.ChatlogRepoJPA;
import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculateFAQStatisticsImplTest {

    @Mock
    private ChatlogRepoJPA chatlogRepoJPA;

    @Mock
    private GetChatbotFAQ getChatbotFAQ;

    @InjectMocks
    private CalculateFAQStatisticsImpl calculateFAQStatistics;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void calculateMostAskedFAQs_ShouldReturnCorrectStatistics() {
        // Given
        List<ChatEntity> chatLogs = createChatLogs();
        when(chatlogRepoJPA.findAll()).thenReturn(chatLogs);

        Map<String, List<String>> keywordMap = new HashMap<>();
        keywordMap.put("FAQ1", Arrays.asList("keyword1", "keyword2"));
        keywordMap.put("FAQ2", Arrays.asList("keyword3", "keyword4"));
        when(getChatbotFAQ.getKeywordMap()).thenReturn(keywordMap);

        // When
        List<FAQStatistics> result = calculateFAQStatistics.calculateMostAskedFAQs(2);

        // Then
        assertEquals(2, result.size());
        assertEquals("FAQ1", result.get(0).getCategory());
        assertEquals(2, result.get(0).getCount());
        assertEquals("FAQ2", result.get(1).getCategory());
        assertEquals(1, result.get(1).getCount());
    }

    @Test
    void calculateOutOfOfficeChats_ShouldReturnCorrectCount() {
        // Given
        List<ChatEntity> chatLogs = createChatLogs();
        when(chatlogRepoJPA.findAll()).thenReturn(chatLogs);

        // When
        int result = calculateFAQStatistics.calculateOutOfOfficeChats();

        // Then
        assertEquals(3, result);
    }

    @Test
    void getFailedQuestions_ShouldReturnCorrectFailedQuestions() {
        // Given
        List<ChatEntity> chatLogs = createChatLogs();
        when(chatlogRepoJPA.findAll()).thenReturn(chatLogs);

        // When
        List<String> result = calculateFAQStatistics.getFailedQuestions();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains("Failed question 1"));
        assertTrue(result.contains("Failed question 2"));
    }

    private List<ChatEntity> createChatLogs() {
        ChatEntity chat1 = new ChatEntity();
        chat1.setMessages(Arrays.asList(
                createMessage("keyword1 question", LocalDateTime.of(2023, 1, 1, 8, 0), false),
                createMessage("keyword2 question", LocalDateTime.of(2023, 1, 1, 9, 0), false),
                createMessage("Failed question 1", LocalDateTime.of(2023, 1, 1, 6, 0), true)
        ));

        ChatEntity chat2 = new ChatEntity();
        chat2.setMessages(Arrays.asList(
                createMessage("keyword3 question", LocalDateTime.of(2023, 1, 1, 20, 0), false),
                createMessage("Failed question 2", LocalDateTime.of(2023, 1, 1, 21, 0), true)
        ));

        return Arrays.asList(chat1, chat2);
    }

    private MessageEntity createMessage(String content, LocalDateTime dateTime, boolean isClosed) {
        MessageEntity message = new MessageEntity();
        message.setMessage(content);
        message.setDateTime(dateTime);

        ChatEntity chat = new ChatEntity();
        chat.setOpen(!isClosed);
        message.setChat(chat);

        UserEntity user = new UserEntity();
        user.setRoles(isClosed ? "CUSTOMER SERVICE" : "USER");
        message.setSendBy(user);

        return message;
    }
}
