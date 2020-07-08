package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class ContatoTipoEnvioDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContatoTipoEnvioDTO.class);
        ContatoTipoEnvioDTO contatoTipoEnvioDTO1 = new ContatoTipoEnvioDTO();
        contatoTipoEnvioDTO1.setId(1L);
        ContatoTipoEnvioDTO contatoTipoEnvioDTO2 = new ContatoTipoEnvioDTO();
        assertThat(contatoTipoEnvioDTO1).isNotEqualTo(contatoTipoEnvioDTO2);
        contatoTipoEnvioDTO2.setId(contatoTipoEnvioDTO1.getId());
        assertThat(contatoTipoEnvioDTO1).isEqualTo(contatoTipoEnvioDTO2);
        contatoTipoEnvioDTO2.setId(2L);
        assertThat(contatoTipoEnvioDTO1).isNotEqualTo(contatoTipoEnvioDTO2);
        contatoTipoEnvioDTO1.setId(null);
        assertThat(contatoTipoEnvioDTO1).isNotEqualTo(contatoTipoEnvioDTO2);
    }
}
