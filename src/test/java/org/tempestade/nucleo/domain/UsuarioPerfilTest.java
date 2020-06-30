package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class UsuarioPerfilTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioPerfil.class);
        UsuarioPerfil usuarioPerfil1 = new UsuarioPerfil();
        usuarioPerfil1.setId(1L);
        UsuarioPerfil usuarioPerfil2 = new UsuarioPerfil();
        usuarioPerfil2.setId(usuarioPerfil1.getId());
        assertThat(usuarioPerfil1).isEqualTo(usuarioPerfil2);
        usuarioPerfil2.setId(2L);
        assertThat(usuarioPerfil1).isNotEqualTo(usuarioPerfil2);
        usuarioPerfil1.setId(null);
        assertThat(usuarioPerfil1).isNotEqualTo(usuarioPerfil2);
    }
}
