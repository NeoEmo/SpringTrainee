package hexlet.trainee.springtrainee.jpaEntity.repository;

import hexlet.trainee.springtrainee.jpaEntity.model.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserJPA,Long> {
}
