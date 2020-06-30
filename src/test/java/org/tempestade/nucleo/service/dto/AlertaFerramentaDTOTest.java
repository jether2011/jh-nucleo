package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlertaFerramentaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertaFerramentaDTO.class);
        AlertaFerramentaDTO alertaFerramentaDTO1 = new AlertaFerramentaDTO();
        alertaFerramentaDTO1.setId(1L);
        AlertaFerramentaDTO alertaFerramentaDTO2 = new AlertaFerramentaDTO();
        assertThat(alertaFerramentaDTO1).isNotEqualTo(alertaFerramentaDTO2);
        alertaFerramentaDTO2.setId(alertaFerramentaDTO1.getId());
        assertThat(alertaFerramentaDTO1).isEqualTo(alertaFerramentaDTO2);
        alertaFerramentaDTO2.setId(2L);
        assertThat(alertaFerramentaDTO1).isNotEqualTo(alertaFerramentaDTO2);
        alertaFerramentaDTO1.setId(null);
        assertThat(alertaFerramentaDTO1).isNotEqualTo(alertaFerramentaDTO2);
    }
}
