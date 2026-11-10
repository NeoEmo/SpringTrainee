package hexlet.trainee.springtrainee.blueprintsPatternsDTO.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "dto_post")
@EntityListeners(AuditingEntityListener.class)
public class DTOPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String slug;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String body;

    @CreatedDate
    private LocalDateTime createdAt;
}
