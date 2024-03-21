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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faqs")
@AllArgsConstructor
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
    public ResponseEntity<Void> deleteFAQ(@PathVariable int faqId){
        deleteFAQ.deleteFAQ(faqId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{faqId}")
    public ResponseEntity<Void> updateFAQ(@PathVariable("faqId") int faqId, @RequestBody @Valid UpdateChatbotFAQRequest request){
        request.setFAQID(faqId);
        updateFAQ.updateChatbotFAQ(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreateChatbotFAQResponse> createFAQ(@RequestBody @Valid CreateChatbotFAQRequest request){
        CreateChatbotFAQResponse response = createFAQ.createFAQ(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
