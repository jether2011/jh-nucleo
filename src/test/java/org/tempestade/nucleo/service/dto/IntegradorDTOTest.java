package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class IntegradorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegradorDTO.class);
        IntegradorDTO integradorDTO1 = new IntegradorDTO();
        integradorDTO1.setId(1L);
        IntegradorDTO integradorDTO2 = new IntegradorDTO();
        assertThat(integradorDTO1).isNotEqualTo(integradorDTO2);
        integradorDTO2.setId(integradorDTO1.getId());
        assertThat(integradorDTO1).isEqualTo(integradorDTO2);
        integradorDTO2.setId(2L);
        assertThat(integradorDTO1).isNotEqualTo(integradorDTO2);
        integradorDTO1.setId(null);
        assertThat(integradorDTO1).isNotEqualTo(integradorDTO2);
    }
}
