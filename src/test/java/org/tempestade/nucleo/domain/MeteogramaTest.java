package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class MeteogramaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meteograma.class);
        Meteograma meteograma1 = new Meteograma();
        meteograma1.setId(1L);
        Meteograma meteograma2 = new Meteograma();
        meteograma2.setId(meteograma1.getId());
        assertThat(meteograma1).isEqualTo(meteograma2);
        meteograma2.setId(2L);
        assertThat(meteograma1).isNotEqualTo(meteograma2);
        meteograma1.setId(null);
        assertThat(meteograma1).isNotEqualTo(meteograma2);
    }
}
