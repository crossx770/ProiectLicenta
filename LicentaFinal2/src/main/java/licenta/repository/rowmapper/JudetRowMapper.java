package licenta.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import licenta.domain.Judet;
import licenta.service.ColumnConverter;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Judet}, with proper type conversions.
 */
@Service
public class JudetRowMapper implements BiFunction<Row, String, Judet> {

    private final ColumnConverter converter;

    public JudetRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Judet} stored in the database.
     */
    @Override
    public Judet apply(Row row, String prefix) {
        Judet entity = new Judet();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        return entity;
    }
}
