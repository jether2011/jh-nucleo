package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoRecursoTipoEnvioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoRecursoTipoEnvio.class);
        PlanoRecursoTipoEnvio planoRecursoTipoEnvio1 = new PlanoRecursoTipoEnvio();
        planoRecursoTipoEnvio1.setId(1L);
        PlanoRecursoTipoEnvio planoRecursoTipoEnvio2 = new PlanoRecursoTipoEnvio();
        planoRecursoTipoEnvio2.setId(planoRecursoTipoEnvio1.getId());
        assertThat(planoRecursoTipoEnvio1).isEqualTo(planoRecursoTipoEnvio2);
        planoRecursoTipoEnvio2.setId(2L);
        assertThat(planoRecursoTipoEnvio1).isNotEqualTo(planoRecursoTipoEnvio2);
        planoRecursoTipoEnvio1.setId(null);
        assertThat(planoRecursoTipoEnvio1).isNotEqualTo(planoRecursoTipoEnvio2);
    }
}
