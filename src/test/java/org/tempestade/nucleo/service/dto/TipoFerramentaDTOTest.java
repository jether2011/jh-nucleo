package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class TipoFerramentaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoFerramentaDTO.class);
        TipoFerramentaDTO tipoFerramentaDTO1 = new TipoFerramentaDTO();
        tipoFerramentaDTO1.setId(1L);
        TipoFerramentaDTO tipoFerramentaDTO2 = new TipoFerramentaDTO();
        assertThat(tipoFerramentaDTO1).isNotEqualTo(tipoFerramentaDTO2);
        tipoFerramentaDTO2.setId(tipoFerramentaDTO1.getId());
        assertThat(tipoFerramentaDTO1).isEqualTo(tipoFerramentaDTO2);
        tipoFerramentaDTO2.setId(2L);
        assertThat(tipoFerramentaDTO1).isNotEqualTo(tipoFerramentaDTO2);
        tipoFerramentaDTO1.setId(null);
        assertThat(tipoFerramentaDTO1).isNotEqualTo(tipoFerramentaDTO2);
    }
}
