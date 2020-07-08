package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlertaRiscoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertaRiscoDTO.class);
        AlertaRiscoDTO alertaRiscoDTO1 = new AlertaRiscoDTO();
        alertaRiscoDTO1.setId(1L);
        AlertaRiscoDTO alertaRiscoDTO2 = new AlertaRiscoDTO();
        assertThat(alertaRiscoDTO1).isNotEqualTo(alertaRiscoDTO2);
        alertaRiscoDTO2.setId(alertaRiscoDTO1.getId());
        assertThat(alertaRiscoDTO1).isEqualTo(alertaRiscoDTO2);
        alertaRiscoDTO2.setId(2L);
        assertThat(alertaRiscoDTO1).isNotEqualTo(alertaRiscoDTO2);
        alertaRiscoDTO1.setId(null);
        assertThat(alertaRiscoDTO1).isNotEqualTo(alertaRiscoDTO2);
    }
}
