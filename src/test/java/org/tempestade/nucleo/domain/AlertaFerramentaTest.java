package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlertaFerramentaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertaFerramenta.class);
        AlertaFerramenta alertaFerramenta1 = new AlertaFerramenta();
        alertaFerramenta1.setId(1L);
        AlertaFerramenta alertaFerramenta2 = new AlertaFerramenta();
        alertaFerramenta2.setId(alertaFerramenta1.getId());
        assertThat(alertaFerramenta1).isEqualTo(alertaFerramenta2);
        alertaFerramenta2.setId(2L);
        assertThat(alertaFerramenta1).isNotEqualTo(alertaFerramenta2);
        alertaFerramenta1.setId(null);
        assertThat(alertaFerramenta1).isNotEqualTo(alertaFerramenta2);
    }
}
