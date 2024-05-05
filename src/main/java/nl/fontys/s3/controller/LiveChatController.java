package nl.fontys.s3.controller;

import nl.fontys.s3.domain.ChatDomains.MessageRequest;
import nl.fontys.s3.domain.LivechatDomains.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LiveChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageRequest send(ChatMessage chatMessage) //MessageRequest messageRequest
    {
        System.out.println(chatMessage);
        return null;
    }

    @MessageMapping("/chats/{chatid}/queue/inboxmessages")
    @SendTo("/topic/messages")
    public void chat(@DestinationVariable("chatid") String id)
    {
        System.out.print(id);
    }

}
