package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class DescargaTipoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescargaTipo.class);
        DescargaTipo descargaTipo1 = new DescargaTipo();
        descargaTipo1.setId(1L);
        DescargaTipo descargaTipo2 = new DescargaTipo();
        descargaTipo2.setId(descargaTipo1.getId());
        assertThat(descargaTipo1).isEqualTo(descargaTipo2);
        descargaTipo2.setId(2L);
        assertThat(descargaTipo1).isNotEqualTo(descargaTipo2);
        descargaTipo1.setId(null);
        assertThat(descargaTipo1).isNotEqualTo(descargaTipo2);
    }
}
