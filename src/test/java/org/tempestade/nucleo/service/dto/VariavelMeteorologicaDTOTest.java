package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class VariavelMeteorologicaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VariavelMeteorologicaDTO.class);
        VariavelMeteorologicaDTO variavelMeteorologicaDTO1 = new VariavelMeteorologicaDTO();
        variavelMeteorologicaDTO1.setId(1L);
        VariavelMeteorologicaDTO variavelMeteorologicaDTO2 = new VariavelMeteorologicaDTO();
        assertThat(variavelMeteorologicaDTO1).isNotEqualTo(variavelMeteorologicaDTO2);
        variavelMeteorologicaDTO2.setId(variavelMeteorologicaDTO1.getId());
        assertThat(variavelMeteorologicaDTO1).isEqualTo(variavelMeteorologicaDTO2);
        variavelMeteorologicaDTO2.setId(2L);
        assertThat(variavelMeteorologicaDTO1).isNotEqualTo(variavelMeteorologicaDTO2);
        variavelMeteorologicaDTO1.setId(null);
        assertThat(variavelMeteorologicaDTO1).isNotEqualTo(variavelMeteorologicaDTO2);
    }
}
