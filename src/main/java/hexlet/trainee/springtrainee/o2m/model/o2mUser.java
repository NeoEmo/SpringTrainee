package hexlet.trainee.springtrainee.o2m.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "o2m_users")
public class o2mUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<o2mPost> posts = new ArrayList<>();

    public void addPost(o2mPost post) {
        posts.add(post);
        post.setAuthor(this);
    }

    public void removePost(o2mPost post) {
        posts.remove(post);
        post.setAuthor(null);
    }
}
