package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class UsuarioPerfilDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioPerfilDTO.class);
        UsuarioPerfilDTO usuarioPerfilDTO1 = new UsuarioPerfilDTO();
        usuarioPerfilDTO1.setId(1L);
        UsuarioPerfilDTO usuarioPerfilDTO2 = new UsuarioPerfilDTO();
        assertThat(usuarioPerfilDTO1).isNotEqualTo(usuarioPerfilDTO2);
        usuarioPerfilDTO2.setId(usuarioPerfilDTO1.getId());
        assertThat(usuarioPerfilDTO1).isEqualTo(usuarioPerfilDTO2);
        usuarioPerfilDTO2.setId(2L);
        assertThat(usuarioPerfilDTO1).isNotEqualTo(usuarioPerfilDTO2);
        usuarioPerfilDTO1.setId(null);
        assertThat(usuarioPerfilDTO1).isNotEqualTo(usuarioPerfilDTO2);
    }
}
