package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class TipoEnvioIntegradorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoEnvioIntegrador.class);
        TipoEnvioIntegrador tipoEnvioIntegrador1 = new TipoEnvioIntegrador();
        tipoEnvioIntegrador1.setId(1L);
        TipoEnvioIntegrador tipoEnvioIntegrador2 = new TipoEnvioIntegrador();
        tipoEnvioIntegrador2.setId(tipoEnvioIntegrador1.getId());
        assertThat(tipoEnvioIntegrador1).isEqualTo(tipoEnvioIntegrador2);
        tipoEnvioIntegrador2.setId(2L);
        assertThat(tipoEnvioIntegrador1).isNotEqualTo(tipoEnvioIntegrador2);
        tipoEnvioIntegrador1.setId(null);
        assertThat(tipoEnvioIntegrador1).isNotEqualTo(tipoEnvioIntegrador2);
    }
}
