package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlvoBloqueioTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlvoBloqueio.class);
        AlvoBloqueio alvoBloqueio1 = new AlvoBloqueio();
        alvoBloqueio1.setId(1L);
        AlvoBloqueio alvoBloqueio2 = new AlvoBloqueio();
        alvoBloqueio2.setId(alvoBloqueio1.getId());
        assertThat(alvoBloqueio1).isEqualTo(alvoBloqueio2);
        alvoBloqueio2.setId(2L);
        assertThat(alvoBloqueio1).isNotEqualTo(alvoBloqueio2);
        alvoBloqueio1.setId(null);
        assertThat(alvoBloqueio1).isNotEqualTo(alvoBloqueio2);
    }
}
