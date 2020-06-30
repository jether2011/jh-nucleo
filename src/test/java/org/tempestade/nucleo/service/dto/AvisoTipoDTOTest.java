package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AvisoTipoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisoTipoDTO.class);
        AvisoTipoDTO avisoTipoDTO1 = new AvisoTipoDTO();
        avisoTipoDTO1.setId(1L);
        AvisoTipoDTO avisoTipoDTO2 = new AvisoTipoDTO();
        assertThat(avisoTipoDTO1).isNotEqualTo(avisoTipoDTO2);
        avisoTipoDTO2.setId(avisoTipoDTO1.getId());
        assertThat(avisoTipoDTO1).isEqualTo(avisoTipoDTO2);
        avisoTipoDTO2.setId(2L);
        assertThat(avisoTipoDTO1).isNotEqualTo(avisoTipoDTO2);
        avisoTipoDTO1.setId(null);
        assertThat(avisoTipoDTO1).isNotEqualTo(avisoTipoDTO2);
    }
}
