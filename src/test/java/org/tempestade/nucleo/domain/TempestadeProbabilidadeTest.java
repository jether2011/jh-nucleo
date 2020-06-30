package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class TempestadeProbabilidadeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TempestadeProbabilidade.class);
        TempestadeProbabilidade tempestadeProbabilidade1 = new TempestadeProbabilidade();
        tempestadeProbabilidade1.setId(1L);
        TempestadeProbabilidade tempestadeProbabilidade2 = new TempestadeProbabilidade();
        tempestadeProbabilidade2.setId(tempestadeProbabilidade1.getId());
        assertThat(tempestadeProbabilidade1).isEqualTo(tempestadeProbabilidade2);
        tempestadeProbabilidade2.setId(2L);
        assertThat(tempestadeProbabilidade1).isNotEqualTo(tempestadeProbabilidade2);
        tempestadeProbabilidade1.setId(null);
        assertThat(tempestadeProbabilidade1).isNotEqualTo(tempestadeProbabilidade2);
    }
}
