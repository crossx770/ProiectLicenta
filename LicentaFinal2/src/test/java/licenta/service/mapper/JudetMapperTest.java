package licenta.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JudetMapperTest {

    private JudetMapper judetMapper;

    @BeforeEach
    public void setUp() {
        judetMapper = new JudetMapperImpl();
    }
}
