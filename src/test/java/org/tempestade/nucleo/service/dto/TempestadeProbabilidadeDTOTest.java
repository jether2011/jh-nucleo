package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class TempestadeProbabilidadeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TempestadeProbabilidadeDTO.class);
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO1 = new TempestadeProbabilidadeDTO();
        tempestadeProbabilidadeDTO1.setId(1L);
        TempestadeProbabilidadeDTO tempestadeProbabilidadeDTO2 = new TempestadeProbabilidadeDTO();
        assertThat(tempestadeProbabilidadeDTO1).isNotEqualTo(tempestadeProbabilidadeDTO2);
        tempestadeProbabilidadeDTO2.setId(tempestadeProbabilidadeDTO1.getId());
        assertThat(tempestadeProbabilidadeDTO1).isEqualTo(tempestadeProbabilidadeDTO2);
        tempestadeProbabilidadeDTO2.setId(2L);
        assertThat(tempestadeProbabilidadeDTO1).isNotEqualTo(tempestadeProbabilidadeDTO2);
        tempestadeProbabilidadeDTO1.setId(null);
        assertThat(tempestadeProbabilidadeDTO1).isNotEqualTo(tempestadeProbabilidadeDTO2);
    }
}
