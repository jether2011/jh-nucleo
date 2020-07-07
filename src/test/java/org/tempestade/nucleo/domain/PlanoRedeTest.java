package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoRedeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoRede.class);
        PlanoRede planoRede1 = new PlanoRede();
        planoRede1.setId(1L);
        PlanoRede planoRede2 = new PlanoRede();
        planoRede2.setId(planoRede1.getId());
        assertThat(planoRede1).isEqualTo(planoRede2);
        planoRede2.setId(2L);
        assertThat(planoRede1).isNotEqualTo(planoRede2);
        planoRede1.setId(null);
        assertThat(planoRede1).isNotEqualTo(planoRede2);
    }
}
