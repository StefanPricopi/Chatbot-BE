package nl.fontys.s3.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.domain.ChatDomains.MessageRequest;
import nl.fontys.s3.domain.LivechatDomains.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class LiveChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageRequest send(ChatMessage chatMessage) //MessageRequest messageRequest
    {
        System.out.println(chatMessage);
        return null;
    }

    @MessageMapping("/chats/{chatid}")
    @SendTo("/topic/messages")
    public void chat(@DestinationVariable("chatid") String id, @Payload String msg)
    {
        System.out.print(id);
        System.out.println(msg);

        messagingTemplate.convertAndSend(1);
    }

}
