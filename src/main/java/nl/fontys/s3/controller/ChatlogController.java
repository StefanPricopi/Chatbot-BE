package nl.fontys.s3.controller;


import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.CreateChatUC;
import nl.fontys.s3.business.ChatLogPck.LogChatUC;
import nl.fontys.s3.domain.MessageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/chat")
@CrossOrigin
public class ChatlogController {

    private CreateChatUC createChatUC;
    private LogChatUC logChatUC;



    @PostMapping("/newchat")
    public void createChatLog()
    {
        // Snap, how will I do this correctly...

        //Example
        createChatUC.createChat(1);
    }

    @PostMapping("/logMsg")
    public void logMessage()
    {
        // saves messages in a log
        // should have an id for the chat to save it to the correct chat.
        logChatUC.logChat(1, MessageRequest.builder()
                        .user_id(1)
                        .message("TestMsg")
                        .build());
    }

    @GetMapping("{id}")
    public void retrieveChat()
    {
        // retrieves chat by id

    }

    @GetMapping("/all")
    public void retrieveLogById(long id)
    {
        // retrieves all chats
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLog()
    {
        // Deletes chat
    }

}
