package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AcumuladoChuvaFaixaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcumuladoChuvaFaixa.class);
        AcumuladoChuvaFaixa acumuladoChuvaFaixa1 = new AcumuladoChuvaFaixa();
        acumuladoChuvaFaixa1.setId(1L);
        AcumuladoChuvaFaixa acumuladoChuvaFaixa2 = new AcumuladoChuvaFaixa();
        acumuladoChuvaFaixa2.setId(acumuladoChuvaFaixa1.getId());
        assertThat(acumuladoChuvaFaixa1).isEqualTo(acumuladoChuvaFaixa2);
        acumuladoChuvaFaixa2.setId(2L);
        assertThat(acumuladoChuvaFaixa1).isNotEqualTo(acumuladoChuvaFaixa2);
        acumuladoChuvaFaixa1.setId(null);
        assertThat(acumuladoChuvaFaixa1).isNotEqualTo(acumuladoChuvaFaixa2);
    }
}
