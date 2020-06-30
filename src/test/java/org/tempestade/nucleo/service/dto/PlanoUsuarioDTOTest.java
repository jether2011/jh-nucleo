package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoUsuarioDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoUsuarioDTO.class);
        PlanoUsuarioDTO planoUsuarioDTO1 = new PlanoUsuarioDTO();
        planoUsuarioDTO1.setId(1L);
        PlanoUsuarioDTO planoUsuarioDTO2 = new PlanoUsuarioDTO();
        assertThat(planoUsuarioDTO1).isNotEqualTo(planoUsuarioDTO2);
        planoUsuarioDTO2.setId(planoUsuarioDTO1.getId());
        assertThat(planoUsuarioDTO1).isEqualTo(planoUsuarioDTO2);
        planoUsuarioDTO2.setId(2L);
        assertThat(planoUsuarioDTO1).isNotEqualTo(planoUsuarioDTO2);
        planoUsuarioDTO1.setId(null);
        assertThat(planoUsuarioDTO1).isNotEqualTo(planoUsuarioDTO2);
    }
}
