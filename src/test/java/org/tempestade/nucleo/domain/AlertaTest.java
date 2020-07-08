package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlertaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alerta.class);
        Alerta alerta1 = new Alerta();
        alerta1.setId(1L);
        Alerta alerta2 = new Alerta();
        alerta2.setId(alerta1.getId());
        assertThat(alerta1).isEqualTo(alerta2);
        alerta2.setId(2L);
        assertThat(alerta1).isNotEqualTo(alerta2);
        alerta1.setId(null);
        assertThat(alerta1).isNotEqualTo(alerta2);
    }
}
