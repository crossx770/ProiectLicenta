package licenta.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import licenta.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JudetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JudetDTO.class);
        JudetDTO judetDTO1 = new JudetDTO();
        judetDTO1.setId(1L);
        JudetDTO judetDTO2 = new JudetDTO();
        assertThat(judetDTO1).isNotEqualTo(judetDTO2);
        judetDTO2.setId(judetDTO1.getId());
        assertThat(judetDTO1).isEqualTo(judetDTO2);
        judetDTO2.setId(2L);
        assertThat(judetDTO1).isNotEqualTo(judetDTO2);
        judetDTO1.setId(null);
        assertThat(judetDTO1).isNotEqualTo(judetDTO2);
    }
}
