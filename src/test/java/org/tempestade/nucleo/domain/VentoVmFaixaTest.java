package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class VentoVmFaixaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VentoVmFaixa.class);
        VentoVmFaixa ventoVmFaixa1 = new VentoVmFaixa();
        ventoVmFaixa1.setId(1L);
        VentoVmFaixa ventoVmFaixa2 = new VentoVmFaixa();
        ventoVmFaixa2.setId(ventoVmFaixa1.getId());
        assertThat(ventoVmFaixa1).isEqualTo(ventoVmFaixa2);
        ventoVmFaixa2.setId(2L);
        assertThat(ventoVmFaixa1).isNotEqualTo(ventoVmFaixa2);
        ventoVmFaixa1.setId(null);
        assertThat(ventoVmFaixa1).isNotEqualTo(ventoVmFaixa2);
    }
}
