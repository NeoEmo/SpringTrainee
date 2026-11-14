package hexlet.trainee.springtrainee.JpaSpecifications.repository;

import hexlet.trainee.springtrainee.JpaSpecifications.model.JPASPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JPASPostRepository extends JpaRepository<JPASPost,Long>, JpaSpecificationExecutor<JPASPost> {
    // Объявлять метод не нужно
    // Он уже есть в JpaSpecificationExecutor
    // Page<Post> findAll(Specification<Post> spec, Pageable pageable);
}
