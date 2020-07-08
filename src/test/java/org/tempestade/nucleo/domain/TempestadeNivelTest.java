package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class TempestadeNivelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TempestadeNivel.class);
        TempestadeNivel tempestadeNivel1 = new TempestadeNivel();
        tempestadeNivel1.setId(1L);
        TempestadeNivel tempestadeNivel2 = new TempestadeNivel();
        tempestadeNivel2.setId(tempestadeNivel1.getId());
        assertThat(tempestadeNivel1).isEqualTo(tempestadeNivel2);
        tempestadeNivel2.setId(2L);
        assertThat(tempestadeNivel1).isNotEqualTo(tempestadeNivel2);
        tempestadeNivel1.setId(null);
        assertThat(tempestadeNivel1).isNotEqualTo(tempestadeNivel2);
    }
}
