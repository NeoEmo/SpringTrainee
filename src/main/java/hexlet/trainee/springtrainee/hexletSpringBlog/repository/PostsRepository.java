package hexlet.trainee.springtrainee.hexletSpringBlog.repository;

import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long> {


}
