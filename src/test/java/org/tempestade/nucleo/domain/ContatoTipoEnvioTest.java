package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class ContatoTipoEnvioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContatoTipoEnvio.class);
        ContatoTipoEnvio contatoTipoEnvio1 = new ContatoTipoEnvio();
        contatoTipoEnvio1.setId(1L);
        ContatoTipoEnvio contatoTipoEnvio2 = new ContatoTipoEnvio();
        contatoTipoEnvio2.setId(contatoTipoEnvio1.getId());
        assertThat(contatoTipoEnvio1).isEqualTo(contatoTipoEnvio2);
        contatoTipoEnvio2.setId(2L);
        assertThat(contatoTipoEnvio1).isNotEqualTo(contatoTipoEnvio2);
        contatoTipoEnvio1.setId(null);
        assertThat(contatoTipoEnvio1).isNotEqualTo(contatoTipoEnvio2);
    }
}
