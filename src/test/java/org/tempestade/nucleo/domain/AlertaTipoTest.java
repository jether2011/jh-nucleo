package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlertaTipoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlertaTipo.class);
        AlertaTipo alertaTipo1 = new AlertaTipo();
        alertaTipo1.setId(1L);
        AlertaTipo alertaTipo2 = new AlertaTipo();
        alertaTipo2.setId(alertaTipo1.getId());
        assertThat(alertaTipo1).isEqualTo(alertaTipo2);
        alertaTipo2.setId(2L);
        assertThat(alertaTipo1).isNotEqualTo(alertaTipo2);
        alertaTipo1.setId(null);
        assertThat(alertaTipo1).isNotEqualTo(alertaTipo2);
    }
}
