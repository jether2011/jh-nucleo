package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class IntensidadeChuvaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntensidadeChuva.class);
        IntensidadeChuva intensidadeChuva1 = new IntensidadeChuva();
        intensidadeChuva1.setId(1L);
        IntensidadeChuva intensidadeChuva2 = new IntensidadeChuva();
        intensidadeChuva2.setId(intensidadeChuva1.getId());
        assertThat(intensidadeChuva1).isEqualTo(intensidadeChuva2);
        intensidadeChuva2.setId(2L);
        assertThat(intensidadeChuva1).isNotEqualTo(intensidadeChuva2);
        intensidadeChuva1.setId(null);
        assertThat(intensidadeChuva1).isNotEqualTo(intensidadeChuva2);
    }
}
