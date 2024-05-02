package nl.fontys.s3.controller;

import nl.fontys.s3.domain.ChatDomains.MessageRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LiveChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageRequest send(MessageRequest messageRequest)
    {
        return messageRequest;
    }
}
