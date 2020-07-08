package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoLayerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoLayer.class);
        PlanoLayer planoLayer1 = new PlanoLayer();
        planoLayer1.setId(1L);
        PlanoLayer planoLayer2 = new PlanoLayer();
        planoLayer2.setId(planoLayer1.getId());
        assertThat(planoLayer1).isEqualTo(planoLayer2);
        planoLayer2.setId(2L);
        assertThat(planoLayer1).isNotEqualTo(planoLayer2);
        planoLayer1.setId(null);
        assertThat(planoLayer1).isNotEqualTo(planoLayer2);
    }
}
