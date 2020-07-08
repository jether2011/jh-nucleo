package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AvisoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisoDTO.class);
        AvisoDTO avisoDTO1 = new AvisoDTO();
        avisoDTO1.setId(1L);
        AvisoDTO avisoDTO2 = new AvisoDTO();
        assertThat(avisoDTO1).isNotEqualTo(avisoDTO2);
        avisoDTO2.setId(avisoDTO1.getId());
        assertThat(avisoDTO1).isEqualTo(avisoDTO2);
        avisoDTO2.setId(2L);
        assertThat(avisoDTO1).isNotEqualTo(avisoDTO2);
        avisoDTO1.setId(null);
        assertThat(avisoDTO1).isNotEqualTo(avisoDTO2);
    }
}
