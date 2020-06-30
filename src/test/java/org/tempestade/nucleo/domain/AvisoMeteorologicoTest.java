package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AvisoMeteorologicoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvisoMeteorologico.class);
        AvisoMeteorologico avisoMeteorologico1 = new AvisoMeteorologico();
        avisoMeteorologico1.setId(1L);
        AvisoMeteorologico avisoMeteorologico2 = new AvisoMeteorologico();
        avisoMeteorologico2.setId(avisoMeteorologico1.getId());
        assertThat(avisoMeteorologico1).isEqualTo(avisoMeteorologico2);
        avisoMeteorologico2.setId(2L);
        assertThat(avisoMeteorologico1).isNotEqualTo(avisoMeteorologico2);
        avisoMeteorologico1.setId(null);
        assertThat(avisoMeteorologico1).isNotEqualTo(avisoMeteorologico2);
    }
}
