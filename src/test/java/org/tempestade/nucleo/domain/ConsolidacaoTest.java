package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class ConsolidacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consolidacao.class);
        Consolidacao consolidacao1 = new Consolidacao();
        consolidacao1.setId(1L);
        Consolidacao consolidacao2 = new Consolidacao();
        consolidacao2.setId(consolidacao1.getId());
        assertThat(consolidacao1).isEqualTo(consolidacao2);
        consolidacao2.setId(2L);
        assertThat(consolidacao1).isNotEqualTo(consolidacao2);
        consolidacao1.setId(null);
        assertThat(consolidacao1).isNotEqualTo(consolidacao2);
    }
}
