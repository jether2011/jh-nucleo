package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class VoceSabiaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoceSabiaDTO.class);
        VoceSabiaDTO voceSabiaDTO1 = new VoceSabiaDTO();
        voceSabiaDTO1.setId(1L);
        VoceSabiaDTO voceSabiaDTO2 = new VoceSabiaDTO();
        assertThat(voceSabiaDTO1).isNotEqualTo(voceSabiaDTO2);
        voceSabiaDTO2.setId(voceSabiaDTO1.getId());
        assertThat(voceSabiaDTO1).isEqualTo(voceSabiaDTO2);
        voceSabiaDTO2.setId(2L);
        assertThat(voceSabiaDTO1).isNotEqualTo(voceSabiaDTO2);
        voceSabiaDTO1.setId(null);
        assertThat(voceSabiaDTO1).isNotEqualTo(voceSabiaDTO2);
    }
}
