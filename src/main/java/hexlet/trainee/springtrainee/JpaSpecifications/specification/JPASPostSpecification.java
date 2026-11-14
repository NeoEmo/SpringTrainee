package hexlet.trainee.springtrainee.JpaSpecifications.specification;

import hexlet.trainee.springtrainee.JpaSpecifications.DTO.JPASPostParamsDTO;
import hexlet.trainee.springtrainee.JpaSpecifications.model.JPASPost;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class JPASPostSpecification {
    public Specification<JPASPost> build(JPASPostParamsDTO params) {
        return withAuthorId(params.getAuthorId()).and(withCreatedAtGreaterThan(params.getCreatedAtGreaterThan()));
    }

    private Specification<JPASPost> withAuthorId(Long authorId) {
        return (root, query, cb) ->
                authorId == null
                        ? cb.conjunction()
                        : cb.equal(root.get("author").get("id"), authorId);
    }

    private Specification<JPASPost> withCreatedAtGreaterThan(LocalDate createdAtGreaterThan) {
        return (root, query, cb) ->
                createdAtGreaterThan == null
                        ? cb.conjunction()
                        : cb.greaterThan(root.get("createdAt"), createdAtGreaterThan.atStartOfDay());

    }
}
