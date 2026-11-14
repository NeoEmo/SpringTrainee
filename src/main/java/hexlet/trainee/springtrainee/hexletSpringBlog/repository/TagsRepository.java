package hexlet.trainee.springtrainee.hexletSpringBlog.repository;

import hexlet.trainee.springtrainee.hexletSpringBlog.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findByOrderByIdDesc(Pageable pageable);
}
