package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoStatus.class);
        PlanoStatus planoStatus1 = new PlanoStatus();
        planoStatus1.setId(1L);
        PlanoStatus planoStatus2 = new PlanoStatus();
        planoStatus2.setId(planoStatus1.getId());
        assertThat(planoStatus1).isEqualTo(planoStatus2);
        planoStatus2.setId(2L);
        assertThat(planoStatus1).isNotEqualTo(planoStatus2);
        planoStatus1.setId(null);
        assertThat(planoStatus1).isNotEqualTo(planoStatus2);
    }
}
