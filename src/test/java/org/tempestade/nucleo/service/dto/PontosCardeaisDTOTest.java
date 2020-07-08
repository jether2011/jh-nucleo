package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PontosCardeaisDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PontosCardeaisDTO.class);
        PontosCardeaisDTO pontosCardeaisDTO1 = new PontosCardeaisDTO();
        pontosCardeaisDTO1.setId(1L);
        PontosCardeaisDTO pontosCardeaisDTO2 = new PontosCardeaisDTO();
        assertThat(pontosCardeaisDTO1).isNotEqualTo(pontosCardeaisDTO2);
        pontosCardeaisDTO2.setId(pontosCardeaisDTO1.getId());
        assertThat(pontosCardeaisDTO1).isEqualTo(pontosCardeaisDTO2);
        pontosCardeaisDTO2.setId(2L);
        assertThat(pontosCardeaisDTO1).isNotEqualTo(pontosCardeaisDTO2);
        pontosCardeaisDTO1.setId(null);
        assertThat(pontosCardeaisDTO1).isNotEqualTo(pontosCardeaisDTO2);
    }
}
