package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AvisoTipoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisoTipo.class);
        AvisoTipo avisoTipo1 = new AvisoTipo();
        avisoTipo1.setId(1L);
        AvisoTipo avisoTipo2 = new AvisoTipo();
        avisoTipo2.setId(avisoTipo1.getId());
        assertThat(avisoTipo1).isEqualTo(avisoTipo2);
        avisoTipo2.setId(2L);
        assertThat(avisoTipo1).isNotEqualTo(avisoTipo2);
        avisoTipo1.setId(null);
        assertThat(avisoTipo1).isNotEqualTo(avisoTipo2);
    }
}
