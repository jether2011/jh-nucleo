package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlvoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alvo.class);
        Alvo alvo1 = new Alvo();
        alvo1.setId(1L);
        Alvo alvo2 = new Alvo();
        alvo2.setId(alvo1.getId());
        assertThat(alvo1).isEqualTo(alvo2);
        alvo2.setId(2L);
        assertThat(alvo1).isNotEqualTo(alvo2);
        alvo1.setId(null);
        assertThat(alvo1).isNotEqualTo(alvo2);
    }
}
