package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class TipoEnvioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoEnvio.class);
        TipoEnvio tipoEnvio1 = new TipoEnvio();
        tipoEnvio1.setId(1L);
        TipoEnvio tipoEnvio2 = new TipoEnvio();
        tipoEnvio2.setId(tipoEnvio1.getId());
        assertThat(tipoEnvio1).isEqualTo(tipoEnvio2);
        tipoEnvio2.setId(2L);
        assertThat(tipoEnvio1).isNotEqualTo(tipoEnvio2);
        tipoEnvio1.setId(null);
        assertThat(tipoEnvio1).isNotEqualTo(tipoEnvio2);
    }
}
