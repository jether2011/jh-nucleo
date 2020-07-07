package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlertaTipoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertaTipoDTO.class);
        AlertaTipoDTO alertaTipoDTO1 = new AlertaTipoDTO();
        alertaTipoDTO1.setId(1L);
        AlertaTipoDTO alertaTipoDTO2 = new AlertaTipoDTO();
        assertThat(alertaTipoDTO1).isNotEqualTo(alertaTipoDTO2);
        alertaTipoDTO2.setId(alertaTipoDTO1.getId());
        assertThat(alertaTipoDTO1).isEqualTo(alertaTipoDTO2);
        alertaTipoDTO2.setId(2L);
        assertThat(alertaTipoDTO1).isNotEqualTo(alertaTipoDTO2);
        alertaTipoDTO1.setId(null);
        assertThat(alertaTipoDTO1).isNotEqualTo(alertaTipoDTO2);
    }
}
