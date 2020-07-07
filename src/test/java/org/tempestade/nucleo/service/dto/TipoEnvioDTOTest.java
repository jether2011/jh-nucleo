package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class TipoEnvioDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoEnvioDTO.class);
        TipoEnvioDTO tipoEnvioDTO1 = new TipoEnvioDTO();
        tipoEnvioDTO1.setId(1L);
        TipoEnvioDTO tipoEnvioDTO2 = new TipoEnvioDTO();
        assertThat(tipoEnvioDTO1).isNotEqualTo(tipoEnvioDTO2);
        tipoEnvioDTO2.setId(tipoEnvioDTO1.getId());
        assertThat(tipoEnvioDTO1).isEqualTo(tipoEnvioDTO2);
        tipoEnvioDTO2.setId(2L);
        assertThat(tipoEnvioDTO1).isNotEqualTo(tipoEnvioDTO2);
        tipoEnvioDTO1.setId(null);
        assertThat(tipoEnvioDTO1).isNotEqualTo(tipoEnvioDTO2);
    }
}
