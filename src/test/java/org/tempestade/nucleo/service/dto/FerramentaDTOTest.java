package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class FerramentaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FerramentaDTO.class);
        FerramentaDTO ferramentaDTO1 = new FerramentaDTO();
        ferramentaDTO1.setId(1L);
        FerramentaDTO ferramentaDTO2 = new FerramentaDTO();
        assertThat(ferramentaDTO1).isNotEqualTo(ferramentaDTO2);
        ferramentaDTO2.setId(ferramentaDTO1.getId());
        assertThat(ferramentaDTO1).isEqualTo(ferramentaDTO2);
        ferramentaDTO2.setId(2L);
        assertThat(ferramentaDTO1).isNotEqualTo(ferramentaDTO2);
        ferramentaDTO1.setId(null);
        assertThat(ferramentaDTO1).isNotEqualTo(ferramentaDTO2);
    }
}
