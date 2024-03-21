package nl.fontys.s3.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateChatbotFAQRequest {

        private int FAQID;
        @NotBlank
        private String question;
        @NotBlank
        private String answer;
        @NotBlank
        private String category;
        @NotBlank
        private Timestamp dateAdded;
}
