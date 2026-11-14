package hexlet.trainee.springtrainee.hexletSpringBlog.repository;

import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long> {
    Page<Post> findByOrderByCreatedAtDesc(Pageable pageable);
    Page<Post> findByAuthorId(Long userId, Pageable pageable);
    Optional<Post> findBySlug(String slug);
}
