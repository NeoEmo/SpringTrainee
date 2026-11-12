package hexlet.trainee.springtrainee.hexletSpringBlog.mappers;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JsonNullableMapper {

    public default <T> JsonNullable<T> wrap(T entity) {
        return JsonNullable.of(entity);
    }

    public default <T> T unwrap(JsonNullable<T> jsonNullable) {
        return jsonNullable == null ? null : jsonNullable.orElse(null);
    }

    @Condition
    public default <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null  && nullable.isPresent();
    }
}
