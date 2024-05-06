package nl.fontys.s3.controller;


import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.*;
import nl.fontys.s3.domain.ChatDomains.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/chat")
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:5174"})
public class ChatlogController {

    private CreateChatUC createChatUC;
    private LogChatUC logChatUC;
    private DeleteChatLogUC deleteChatLogUC;
    private RetrieveChatUC retrieveChatUC;
    private RetrieveAllChatsUC retrieveAllChatsUC;



    @PostMapping("/newchat")
    public ResponseEntity<CreateChatResponse> createChatLog(@RequestBody MessageRequest chatRequest)
    {
        // Snap, how will I do this correctly...

        //Example
        try
        {
            CreateChatResponse chat = createChatUC.createChat(chatRequest.getSendBy().getUserId(), chatRequest);


            System.out.println("So fires here: " + chat.getChat_id());
            return ResponseEntity.ok().body(chat);
        }
        catch(Exception e)
        {
            System.out.println("user with id doesn't exist");
            return ResponseEntity.notFound().build();
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
            System.out.println("\n\n\n\n So it receives a message. \n\n\n" + request.getChat_id() + "  " + request.getMessage());
            return ResponseEntity.ok().body("added chatlog");
        }
        catch(Exception e)
        {
            System.out.println("Something went wrong and i'm nut exactlysure what. " +  e.getMessage());
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
            return ResponseEntity.notFound().build();
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
