package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class InformativoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Informativo.class);
        Informativo informativo1 = new Informativo();
        informativo1.setId(1L);
        Informativo informativo2 = new Informativo();
        informativo2.setId(informativo1.getId());
        assertThat(informativo1).isEqualTo(informativo2);
        informativo2.setId(2L);
        assertThat(informativo1).isNotEqualTo(informativo2);
        informativo1.setId(null);
        assertThat(informativo1).isNotEqualTo(informativo2);
    }
}
