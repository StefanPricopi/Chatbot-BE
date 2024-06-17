package nl.fontys.s3.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.business.CreateChatbotFAQ;
import nl.fontys.s3.business.DeleteChatbotFAQ;
import nl.fontys.s3.business.GetChatbotFAQ;
import nl.fontys.s3.business.UpdateChatbotFAQ;
import nl.fontys.s3.business.impl.BidService;
import nl.fontys.s3.business.impl.CalculateFAQStatistics;
import nl.fontys.s3.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nl.fontys.s3.domain.chatbotFAQ.*;

import java.util.List;

@RestController
@RequestMapping("/faqs")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ChatbotFAQController {
    private final CreateChatbotFAQ createFAQ;
    private final GetChatbotFAQ getFAQs;
    private final DeleteChatbotFAQ deleteFAQ;
    private final UpdateChatbotFAQ updateFAQ;
    private final BidService bidService;
    private final CalculateFAQStatistics calculateFAQStatistics;
    @GetMapping()
    public ResponseEntity<GetAllChatbotFAQResponse> getAllFAQs(){
        GetAllChatbotFAQResponse response = getFAQs.getFAQ();
        for (int i = 0 ;i<response.getChatbotFAQS().size();i++)
        {
            System.out.println(response.getChatbotFAQS().get(i));
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{faqId}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Long faqId){
        deleteFAQ.deleteFAQ(Math.toIntExact(faqId));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{faqId}")
    public ResponseEntity<Void> updateFAQ(@PathVariable("faqId") Long faqId, @RequestBody @Valid UpdateChatbotFAQRequest request){
        request.setFAQID(faqId);
        updateFAQ.updateChatbotFAQ(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreateChatbotFAQResponse> createFAQ(@RequestBody @Valid CreateChatbotFAQRequest request){
        CreateChatbotFAQResponse response = createFAQ.createFAQ(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/getChatbotResponse")
    public String getChatbotResponse(@RequestBody ChatbotRequest request) {
        String response = getFAQs.processUserQuery(request.getMessage(), request.getUserId(), request.getAttempts(), request.isEndOfConversation());
        return response != null ? response : "I'm sorry, I couldn't find an answer to your question.";
    }

    @GetMapping("/outOfOfficeChats")
    public int getOutOfOfficeChats() {
        return calculateFAQStatistics.calculateOutOfOfficeChats();
    }


    @GetMapping("/statistics")
    public List<FAQStatistics> getFAQStatistics() {
        return calculateFAQStatistics.calculateMostAskedFAQs(10);
    }

    @GetMapping("/failedQuestions")
    public ResponseEntity<List<String>> getFailedQuestions() {
        List<String> failedQuestions = calculateFAQStatistics.getFailedQuestions();
        return ResponseEntity.ok(failedQuestions);
    }
}
