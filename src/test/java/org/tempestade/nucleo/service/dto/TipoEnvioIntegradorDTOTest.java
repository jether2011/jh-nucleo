package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class TipoEnvioIntegradorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoEnvioIntegradorDTO.class);
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO1 = new TipoEnvioIntegradorDTO();
        tipoEnvioIntegradorDTO1.setId(1L);
        TipoEnvioIntegradorDTO tipoEnvioIntegradorDTO2 = new TipoEnvioIntegradorDTO();
        assertThat(tipoEnvioIntegradorDTO1).isNotEqualTo(tipoEnvioIntegradorDTO2);
        tipoEnvioIntegradorDTO2.setId(tipoEnvioIntegradorDTO1.getId());
        assertThat(tipoEnvioIntegradorDTO1).isEqualTo(tipoEnvioIntegradorDTO2);
        tipoEnvioIntegradorDTO2.setId(2L);
        assertThat(tipoEnvioIntegradorDTO1).isNotEqualTo(tipoEnvioIntegradorDTO2);
        tipoEnvioIntegradorDTO1.setId(null);
        assertThat(tipoEnvioIntegradorDTO1).isNotEqualTo(tipoEnvioIntegradorDTO2);
    }
}
