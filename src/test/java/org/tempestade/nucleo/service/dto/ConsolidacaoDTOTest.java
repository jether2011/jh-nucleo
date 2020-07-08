package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class ConsolidacaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsolidacaoDTO.class);
        ConsolidacaoDTO consolidacaoDTO1 = new ConsolidacaoDTO();
        consolidacaoDTO1.setId(1L);
        ConsolidacaoDTO consolidacaoDTO2 = new ConsolidacaoDTO();
        assertThat(consolidacaoDTO1).isNotEqualTo(consolidacaoDTO2);
        consolidacaoDTO2.setId(consolidacaoDTO1.getId());
        assertThat(consolidacaoDTO1).isEqualTo(consolidacaoDTO2);
        consolidacaoDTO2.setId(2L);
        assertThat(consolidacaoDTO1).isNotEqualTo(consolidacaoDTO2);
        consolidacaoDTO1.setId(null);
        assertThat(consolidacaoDTO1).isNotEqualTo(consolidacaoDTO2);
    }
}
