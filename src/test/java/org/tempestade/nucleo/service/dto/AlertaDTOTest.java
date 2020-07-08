package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlertaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertaDTO.class);
        AlertaDTO alertaDTO1 = new AlertaDTO();
        alertaDTO1.setId(1L);
        AlertaDTO alertaDTO2 = new AlertaDTO();
        assertThat(alertaDTO1).isNotEqualTo(alertaDTO2);
        alertaDTO2.setId(alertaDTO1.getId());
        assertThat(alertaDTO1).isEqualTo(alertaDTO2);
        alertaDTO2.setId(2L);
        assertThat(alertaDTO1).isNotEqualTo(alertaDTO2);
        alertaDTO1.setId(null);
        assertThat(alertaDTO1).isNotEqualTo(alertaDTO2);
    }
}
