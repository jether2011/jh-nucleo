package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PrevisaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Previsao.class);
        Previsao previsao1 = new Previsao();
        previsao1.setId(1L);
        Previsao previsao2 = new Previsao();
        previsao2.setId(previsao1.getId());
        assertThat(previsao1).isEqualTo(previsao2);
        previsao2.setId(2L);
        assertThat(previsao1).isNotEqualTo(previsao2);
        previsao1.setId(null);
        assertThat(previsao1).isNotEqualTo(previsao2);
    }
}
