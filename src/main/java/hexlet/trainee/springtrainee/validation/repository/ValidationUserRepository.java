package hexlet.trainee.springtrainee.validation.repository;

import hexlet.trainee.springtrainee.validation.model.ValidationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationUserRepository extends JpaRepository<ValidationUser, Long> {

}
