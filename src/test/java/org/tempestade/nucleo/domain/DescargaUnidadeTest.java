package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class DescargaUnidadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescargaUnidade.class);
        DescargaUnidade descargaUnidade1 = new DescargaUnidade();
        descargaUnidade1.setId(1L);
        DescargaUnidade descargaUnidade2 = new DescargaUnidade();
        descargaUnidade2.setId(descargaUnidade1.getId());
        assertThat(descargaUnidade1).isEqualTo(descargaUnidade2);
        descargaUnidade2.setId(2L);
        assertThat(descargaUnidade1).isNotEqualTo(descargaUnidade2);
        descargaUnidade1.setId(null);
        assertThat(descargaUnidade1).isNotEqualTo(descargaUnidade2);
    }
}
