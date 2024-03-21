package nl.fontys.s3.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.Timestamp;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatbotFAQEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int FAQID;
        private String question;
        private String answer;
        private String category;
        private Timestamp dateAdded;

}
