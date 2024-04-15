package nl.fontys.s3.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.business.CreateChatbotFAQ;
import nl.fontys.s3.business.DeleteChatbotFAQ;
import nl.fontys.s3.business.GetChatbotFAQ;
import nl.fontys.s3.business.UpdateChatbotFAQ;
import nl.fontys.s3.domain.CreateChatbotFAQRequest;
import nl.fontys.s3.domain.CreateChatbotFAQResponse;
import nl.fontys.s3.domain.GetAllChatbotFAQResponse;
import nl.fontys.s3.domain.UpdateChatbotFAQRequest;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public ResponseEntity<GetAllChatbotFAQResponse> getAllFAQs(){
        GetAllChatbotFAQResponse response = getFAQs.getFAQ();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{faqId}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Long faqId){
        deleteFAQ.deleteFAQ(faqId);
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
    @GetMapping("/search")
    public ResponseEntity<List<ChatbotFAQEntity>> getFAQsByKeyword(@RequestParam String keyword) {
        List<ChatbotFAQEntity> faqs = getFAQs.getFAQsByKeyword(keyword);
        return ResponseEntity.ok(faqs);
    }

}

