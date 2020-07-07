package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PrevisaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrevisaoDTO.class);
        PrevisaoDTO previsaoDTO1 = new PrevisaoDTO();
        previsaoDTO1.setId(1L);
        PrevisaoDTO previsaoDTO2 = new PrevisaoDTO();
        assertThat(previsaoDTO1).isNotEqualTo(previsaoDTO2);
        previsaoDTO2.setId(previsaoDTO1.getId());
        assertThat(previsaoDTO1).isEqualTo(previsaoDTO2);
        previsaoDTO2.setId(2L);
        assertThat(previsaoDTO1).isNotEqualTo(previsaoDTO2);
        previsaoDTO1.setId(null);
        assertThat(previsaoDTO1).isNotEqualTo(previsaoDTO2);
    }
}
