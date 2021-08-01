package licenta.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import licenta.domain.SubCategory;
import licenta.service.ColumnConverter;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link SubCategory}, with proper type conversions.
 */
@Service
public class SubCategoryRowMapper implements BiFunction<Row, String, SubCategory> {

    private final ColumnConverter converter;

    public SubCategoryRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link SubCategory} stored in the database.
     */
    @Override
    public SubCategory apply(Row row, String prefix) {
        SubCategory entity = new SubCategory();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setCategoryId(converter.fromRow(row, prefix + "_category_id", Long.class));
        return entity;
    }
}
