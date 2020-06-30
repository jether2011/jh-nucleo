package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlertaRiscoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertaRisco.class);
        AlertaRisco alertaRisco1 = new AlertaRisco();
        alertaRisco1.setId(1L);
        AlertaRisco alertaRisco2 = new AlertaRisco();
        alertaRisco2.setId(alertaRisco1.getId());
        assertThat(alertaRisco1).isEqualTo(alertaRisco2);
        alertaRisco2.setId(2L);
        assertThat(alertaRisco1).isNotEqualTo(alertaRisco2);
        alertaRisco1.setId(null);
        assertThat(alertaRisco1).isNotEqualTo(alertaRisco2);
    }
}
