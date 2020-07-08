package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class DescargaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Descarga.class);
        Descarga descarga1 = new Descarga();
        descarga1.setId(1L);
        Descarga descarga2 = new Descarga();
        descarga2.setId(descarga1.getId());
        assertThat(descarga1).isEqualTo(descarga2);
        descarga2.setId(2L);
        assertThat(descarga1).isNotEqualTo(descarga2);
        descarga1.setId(null);
        assertThat(descarga1).isNotEqualTo(descarga2);
    }
}
