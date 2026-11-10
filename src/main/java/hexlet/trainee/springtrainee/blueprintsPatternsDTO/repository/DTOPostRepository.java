package hexlet.trainee.springtrainee.blueprintsPatternsDTO.repository;

import hexlet.trainee.springtrainee.blueprintsPatternsDTO.model.DTOPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DTOPostRepository extends JpaRepository<DTOPost,Long> {
}
