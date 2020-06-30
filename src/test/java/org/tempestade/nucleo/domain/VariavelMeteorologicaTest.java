package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class VariavelMeteorologicaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VariavelMeteorologica.class);
        VariavelMeteorologica variavelMeteorologica1 = new VariavelMeteorologica();
        variavelMeteorologica1.setId(1L);
        VariavelMeteorologica variavelMeteorologica2 = new VariavelMeteorologica();
        variavelMeteorologica2.setId(variavelMeteorologica1.getId());
        assertThat(variavelMeteorologica1).isEqualTo(variavelMeteorologica2);
        variavelMeteorologica2.setId(2L);
        assertThat(variavelMeteorologica1).isNotEqualTo(variavelMeteorologica2);
        variavelMeteorologica1.setId(null);
        assertThat(variavelMeteorologica1).isNotEqualTo(variavelMeteorologica2);
    }
}
