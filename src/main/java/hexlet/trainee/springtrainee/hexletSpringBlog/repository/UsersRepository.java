package hexlet.trainee.springtrainee.hexletSpringBlog.repository;

import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {


}
