package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class UmidadeArDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UmidadeArDTO.class);
        UmidadeArDTO umidadeArDTO1 = new UmidadeArDTO();
        umidadeArDTO1.setId(1L);
        UmidadeArDTO umidadeArDTO2 = new UmidadeArDTO();
        assertThat(umidadeArDTO1).isNotEqualTo(umidadeArDTO2);
        umidadeArDTO2.setId(umidadeArDTO1.getId());
        assertThat(umidadeArDTO1).isEqualTo(umidadeArDTO2);
        umidadeArDTO2.setId(2L);
        assertThat(umidadeArDTO1).isNotEqualTo(umidadeArDTO2);
        umidadeArDTO1.setId(null);
        assertThat(umidadeArDTO1).isNotEqualTo(umidadeArDTO2);
    }
}
