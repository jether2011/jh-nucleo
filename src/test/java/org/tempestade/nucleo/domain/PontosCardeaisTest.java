package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PontosCardeaisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PontosCardeais.class);
        PontosCardeais pontosCardeais1 = new PontosCardeais();
        pontosCardeais1.setId(1L);
        PontosCardeais pontosCardeais2 = new PontosCardeais();
        pontosCardeais2.setId(pontosCardeais1.getId());
        assertThat(pontosCardeais1).isEqualTo(pontosCardeais2);
        pontosCardeais2.setId(2L);
        assertThat(pontosCardeais1).isNotEqualTo(pontosCardeais2);
        pontosCardeais1.setId(null);
        assertThat(pontosCardeais1).isNotEqualTo(pontosCardeais2);
    }
}
