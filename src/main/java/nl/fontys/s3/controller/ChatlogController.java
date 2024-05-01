package nl.fontys.s3.controller;


import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.*;
import nl.fontys.s3.domain.ChatDomains.AddMsgToChatRequest;
import nl.fontys.s3.domain.ChatDomains.GetAllChatsResponse;
import nl.fontys.s3.domain.ChatDomains.ReadChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatlogController {

    private CreateChatUC createChatUC;
    private LogChatUC logChatUC;
    private DeleteChatLogUC deleteChatLogUC;
    private RetrieveChatUC retrieveChatUC;
    private RetrieveAllChatsUC retrieveAllChatsUC;



    @PostMapping("/newchat")
    public ResponseEntity<String> createChatLog()
    {
        // Snap, how will I do this correctly...

        //Example
        try
        {
            createChatUC.createChat(1);
            return ResponseEntity.ok().body("Chat has been created/opened");
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/logMsg")
    public ResponseEntity<String> logMessage(@RequestBody AddMsgToChatRequest request)
    {
        // saves messages in a log
        // should have an id for the chat to save it to the correct chat.
        try
        {
            logChatUC.logChat(request.getChat_id(), request.getMessage());
            return ResponseEntity.ok().body("");
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().body("Error " + e.getMessage());
        }


    }

    @GetMapping("/all")
    public ResponseEntity<GetAllChatsResponse> getAllChats()
    {
        try
        {
            return ResponseEntity.ok().body(retrieveAllChatsUC.getAllChats());
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadChatResponse> retrieveChatById(@PathVariable(value = "id") long id)
    {
        // retrieves all chats
        try
        {
            return ResponseEntity.ok().body(retrieveChatUC.retrieveChat(id));

        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().build();
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLog(@PathVariable(value = "id") long id)
    {
        // Deletes chat
        try
        {
            deleteChatLogUC.deleteChat(id);
            return ResponseEntity.ok().body("chat deleted");
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().body("Error deleting chat \'" + e.getMessage() + " \'");
        }
    }

}
