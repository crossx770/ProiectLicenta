package licenta.domain;

import static org.assertj.core.api.Assertions.assertThat;

import licenta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JudetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Judet.class);
        Judet judet1 = new Judet();
        judet1.setId(1L);
        Judet judet2 = new Judet();
        judet2.setId(judet1.getId());
        assertThat(judet1).isEqualTo(judet2);
        judet2.setId(2L);
        assertThat(judet1).isNotEqualTo(judet2);
        judet1.setId(null);
        assertThat(judet1).isNotEqualTo(judet2);
    }
}
