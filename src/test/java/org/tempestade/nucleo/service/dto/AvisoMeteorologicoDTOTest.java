package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AvisoMeteorologicoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisoMeteorologicoDTO.class);
        AvisoMeteorologicoDTO avisoMeteorologicoDTO1 = new AvisoMeteorologicoDTO();
        avisoMeteorologicoDTO1.setId(1L);
        AvisoMeteorologicoDTO avisoMeteorologicoDTO2 = new AvisoMeteorologicoDTO();
        assertThat(avisoMeteorologicoDTO1).isNotEqualTo(avisoMeteorologicoDTO2);
        avisoMeteorologicoDTO2.setId(avisoMeteorologicoDTO1.getId());
        assertThat(avisoMeteorologicoDTO1).isEqualTo(avisoMeteorologicoDTO2);
        avisoMeteorologicoDTO2.setId(2L);
        assertThat(avisoMeteorologicoDTO1).isNotEqualTo(avisoMeteorologicoDTO2);
        avisoMeteorologicoDTO1.setId(null);
        assertThat(avisoMeteorologicoDTO1).isNotEqualTo(avisoMeteorologicoDTO2);
    }
}
