package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class TipoFerramentaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoFerramenta.class);
        TipoFerramenta tipoFerramenta1 = new TipoFerramenta();
        tipoFerramenta1.setId(1L);
        TipoFerramenta tipoFerramenta2 = new TipoFerramenta();
        tipoFerramenta2.setId(tipoFerramenta1.getId());
        assertThat(tipoFerramenta1).isEqualTo(tipoFerramenta2);
        tipoFerramenta2.setId(2L);
        assertThat(tipoFerramenta1).isNotEqualTo(tipoFerramenta2);
        tipoFerramenta1.setId(null);
        assertThat(tipoFerramenta1).isNotEqualTo(tipoFerramenta2);
    }
}
