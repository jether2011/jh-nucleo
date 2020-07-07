package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class DescargaTipoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescargaTipoDTO.class);
        DescargaTipoDTO descargaTipoDTO1 = new DescargaTipoDTO();
        descargaTipoDTO1.setId(1L);
        DescargaTipoDTO descargaTipoDTO2 = new DescargaTipoDTO();
        assertThat(descargaTipoDTO1).isNotEqualTo(descargaTipoDTO2);
        descargaTipoDTO2.setId(descargaTipoDTO1.getId());
        assertThat(descargaTipoDTO1).isEqualTo(descargaTipoDTO2);
        descargaTipoDTO2.setId(2L);
        assertThat(descargaTipoDTO1).isNotEqualTo(descargaTipoDTO2);
        descargaTipoDTO1.setId(null);
        assertThat(descargaTipoDTO1).isNotEqualTo(descargaTipoDTO2);
    }
}
