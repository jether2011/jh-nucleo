package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoUsuarioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoUsuario.class);
        PlanoUsuario planoUsuario1 = new PlanoUsuario();
        planoUsuario1.setId(1L);
        PlanoUsuario planoUsuario2 = new PlanoUsuario();
        planoUsuario2.setId(planoUsuario1.getId());
        assertThat(planoUsuario1).isEqualTo(planoUsuario2);
        planoUsuario2.setId(2L);
        assertThat(planoUsuario1).isNotEqualTo(planoUsuario2);
        planoUsuario1.setId(null);
        assertThat(planoUsuario1).isNotEqualTo(planoUsuario2);
    }
}
