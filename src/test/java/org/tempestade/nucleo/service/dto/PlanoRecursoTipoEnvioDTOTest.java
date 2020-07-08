package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoRecursoTipoEnvioDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoRecursoTipoEnvioDTO.class);
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO1 = new PlanoRecursoTipoEnvioDTO();
        planoRecursoTipoEnvioDTO1.setId(1L);
        PlanoRecursoTipoEnvioDTO planoRecursoTipoEnvioDTO2 = new PlanoRecursoTipoEnvioDTO();
        assertThat(planoRecursoTipoEnvioDTO1).isNotEqualTo(planoRecursoTipoEnvioDTO2);
        planoRecursoTipoEnvioDTO2.setId(planoRecursoTipoEnvioDTO1.getId());
        assertThat(planoRecursoTipoEnvioDTO1).isEqualTo(planoRecursoTipoEnvioDTO2);
        planoRecursoTipoEnvioDTO2.setId(2L);
        assertThat(planoRecursoTipoEnvioDTO1).isNotEqualTo(planoRecursoTipoEnvioDTO2);
        planoRecursoTipoEnvioDTO1.setId(null);
        assertThat(planoRecursoTipoEnvioDTO1).isNotEqualTo(planoRecursoTipoEnvioDTO2);
    }
}
