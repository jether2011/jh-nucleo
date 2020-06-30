package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class DescargaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescargaDTO.class);
        DescargaDTO descargaDTO1 = new DescargaDTO();
        descargaDTO1.setId(1L);
        DescargaDTO descargaDTO2 = new DescargaDTO();
        assertThat(descargaDTO1).isNotEqualTo(descargaDTO2);
        descargaDTO2.setId(descargaDTO1.getId());
        assertThat(descargaDTO1).isEqualTo(descargaDTO2);
        descargaDTO2.setId(2L);
        assertThat(descargaDTO1).isNotEqualTo(descargaDTO2);
        descargaDTO1.setId(null);
        assertThat(descargaDTO1).isNotEqualTo(descargaDTO2);
    }
}
