package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class CondicaoTempoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CondicaoTempoDTO.class);
        CondicaoTempoDTO condicaoTempoDTO1 = new CondicaoTempoDTO();
        condicaoTempoDTO1.setId(1L);
        CondicaoTempoDTO condicaoTempoDTO2 = new CondicaoTempoDTO();
        assertThat(condicaoTempoDTO1).isNotEqualTo(condicaoTempoDTO2);
        condicaoTempoDTO2.setId(condicaoTempoDTO1.getId());
        assertThat(condicaoTempoDTO1).isEqualTo(condicaoTempoDTO2);
        condicaoTempoDTO2.setId(2L);
        assertThat(condicaoTempoDTO1).isNotEqualTo(condicaoTempoDTO2);
        condicaoTempoDTO1.setId(null);
        assertThat(condicaoTempoDTO1).isNotEqualTo(condicaoTempoDTO2);
    }
}
