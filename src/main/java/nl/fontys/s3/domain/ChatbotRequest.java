package nl.fontys.s3.domain;

import lombok.Data;

@Data
public class ChatbotRequest {
    private String message;
    private int userId;
}
