package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class UmidadeArTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UmidadeAr.class);
        UmidadeAr umidadeAr1 = new UmidadeAr();
        umidadeAr1.setId(1L);
        UmidadeAr umidadeAr2 = new UmidadeAr();
        umidadeAr2.setId(umidadeAr1.getId());
        assertThat(umidadeAr1).isEqualTo(umidadeAr2);
        umidadeAr2.setId(2L);
        assertThat(umidadeAr1).isNotEqualTo(umidadeAr2);
        umidadeAr1.setId(null);
        assertThat(umidadeAr1).isNotEqualTo(umidadeAr2);
    }
}
