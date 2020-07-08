package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class FerramentaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ferramenta.class);
        Ferramenta ferramenta1 = new Ferramenta();
        ferramenta1.setId(1L);
        Ferramenta ferramenta2 = new Ferramenta();
        ferramenta2.setId(ferramenta1.getId());
        assertThat(ferramenta1).isEqualTo(ferramenta2);
        ferramenta2.setId(2L);
        assertThat(ferramenta1).isNotEqualTo(ferramenta2);
        ferramenta1.setId(null);
        assertThat(ferramenta1).isNotEqualTo(ferramenta2);
    }
}
