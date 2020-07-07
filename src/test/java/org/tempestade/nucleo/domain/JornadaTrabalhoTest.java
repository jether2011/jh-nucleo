package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class JornadaTrabalhoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JornadaTrabalho.class);
        JornadaTrabalho jornadaTrabalho1 = new JornadaTrabalho();
        jornadaTrabalho1.setId(1L);
        JornadaTrabalho jornadaTrabalho2 = new JornadaTrabalho();
        jornadaTrabalho2.setId(jornadaTrabalho1.getId());
        assertThat(jornadaTrabalho1).isEqualTo(jornadaTrabalho2);
        jornadaTrabalho2.setId(2L);
        assertThat(jornadaTrabalho1).isNotEqualTo(jornadaTrabalho2);
        jornadaTrabalho1.setId(null);
        assertThat(jornadaTrabalho1).isNotEqualTo(jornadaTrabalho2);
    }
}
