package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class CondicaoTempoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CondicaoTempo.class);
        CondicaoTempo condicaoTempo1 = new CondicaoTempo();
        condicaoTempo1.setId(1L);
        CondicaoTempo condicaoTempo2 = new CondicaoTempo();
        condicaoTempo2.setId(condicaoTempo1.getId());
        assertThat(condicaoTempo1).isEqualTo(condicaoTempo2);
        condicaoTempo2.setId(2L);
        assertThat(condicaoTempo1).isNotEqualTo(condicaoTempo2);
        condicaoTempo1.setId(null);
        assertThat(condicaoTempo1).isNotEqualTo(condicaoTempo2);
    }
}
