package nl.fontys.s3.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chatlogs")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "createdBy", nullable = false)
    private UserEntity customer;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MessageEntity> messages;

    // Add date
    @CreationTimestamp
    @Column(name = "createdAt", columnDefinition = "TIMESTAMP", updatable = false)
    private LocalDateTime dateTime;

    @Column(name = "solved")
    private boolean isOpen;
}
