package nl.fontys.s3.persistence.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import nl.fontys.s3.domain.ChatDomains.SendByDTO;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chatmessages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity sendBy;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    @JsonBackReference
    private ChatEntity chat;


    @Column(name = "message")
    private String message;

    @CreationTimestamp
    @Column(name = "date_time", columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime dateTime;

}
