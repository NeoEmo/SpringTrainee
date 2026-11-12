package hexlet.trainee.springtrainee.patchUpdate.repository;

import hexlet.trainee.springtrainee.patchUpdate.model.PatchUpdatePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatchUpdatePostRepository extends JpaRepository<PatchUpdatePost,Long> {

}
