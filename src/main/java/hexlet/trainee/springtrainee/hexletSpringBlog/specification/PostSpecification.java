package hexlet.trainee.springtrainee.hexletSpringBlog.specification;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostParamsDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.Tag;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PostSpecification {
    public Specification<Post> build(PostParamsDTO params) {
        return  withAuthorId(params.getAuthorId())
                .and(withCreatedAtGt(params.getCreatedAtGreaterThan()))
                .and(withCreatedAtLt(params.getCreatedAtLessThan()))
                .and(withNameContains(params.getNameContains()))
                .and(withTagsIn(params.getTagsIn()))
                .and(withTagsId(params.getTagId()));
    }

    private Specification<Post> withAuthorId(Long authorId) {
        return (root, query, criteriaBuilder) ->
                authorId == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("author").get("id"), authorId);
    }

    private Specification<Post> withCreatedAtGt(LocalDate createdAtGt) {
        return (root, query, criteriaBuilder) ->
                createdAtGt == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.greaterThan(root.get("createdAt"), createdAtGt.atStartOfDay());
    }

    private Specification<Post> withCreatedAtLt(LocalDate createdAtLt) {
        return (root, query, criteriaBuilder) ->
                createdAtLt == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.lessThan(root.get("createdAt"), createdAtLt.atStartOfDay());
    }

    private Specification<Post> withNameContains(String nameContains) {
        return (root, query, criteriaBuilder) ->
                nameContains == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")), "%" +nameContains.toLowerCase() + "%");
    }

    private Specification<Post> withTagsIn(String tagsIn) {
        return (root, query, criteriaBuilder) -> {
            if (tagsIn == null) return criteriaBuilder.conjunction();

            Join<Post, Tag> tagJoin = root.join("tags");
            query.distinct(true);
            return criteriaBuilder.like(
                    criteriaBuilder.lower(tagJoin.get("name")), "%" + tagsIn.toLowerCase() + "%");
        };

    }

    private Specification<Post> withTagsId(Long tagsId) {
        return (root, query, criteriaBuilder) -> {
            if (tagsId == null) return criteriaBuilder.conjunction();

            Join<Post, Tag> tagJoin = root.join("tags");
            query.distinct(true);

            return criteriaBuilder.equal(tagJoin.get("id"), tagsId);
        };
    }
}
