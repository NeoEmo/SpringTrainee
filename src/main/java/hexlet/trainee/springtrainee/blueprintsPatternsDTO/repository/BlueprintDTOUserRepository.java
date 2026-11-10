package hexlet.trainee.springtrainee.blueprintsPatternsDTO.repository;

import hexlet.trainee.springtrainee.blueprintsPatternsDTO.model.DTOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlueprintDTOUserRepository extends JpaRepository<DTOUser, Long> {
}
