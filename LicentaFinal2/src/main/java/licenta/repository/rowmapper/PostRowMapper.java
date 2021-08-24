package licenta.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import licenta.domain.Post;
import licenta.service.ColumnConverter;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Post}, with proper type conversions.
 */
@Service
public class PostRowMapper implements BiFunction<Row, String, Post> {

    private final ColumnConverter converter;

    public PostRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Post} stored in the database.
     */
    @Override
    public Post apply(Row row, String prefix) {
        Post entity = new Post();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setTitle(converter.fromRow(row, prefix + "_title", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setIs_promoted(converter.fromRow(row, prefix + "_is_promoted", Boolean.class));
        entity.setCreated_at(converter.fromRow(row, prefix + "_created_at", Instant.class));
        entity.setPrice(converter.fromRow(row, prefix + "_price", Float.class));
        entity.setUser_postId(converter.fromRow(row, prefix + "_user_post_id", Long.class));
        entity.setJudet_postId(converter.fromRow(row, prefix + "_judet_post_id", Long.class));
        entity.setCity_postId(converter.fromRow(row, prefix + "_city_post_id", Long.class));
        entity.setCategory_postId(converter.fromRow(row, prefix + "_category_post_id", Long.class));
        entity.setSubCategory_postId(converter.fromRow(row, prefix + "_sub_category_post_id", Long.class));
        return entity;
    }
}
