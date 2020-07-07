package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoRecursoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoRecurso.class);
        PlanoRecurso planoRecurso1 = new PlanoRecurso();
        planoRecurso1.setId(1L);
        PlanoRecurso planoRecurso2 = new PlanoRecurso();
        planoRecurso2.setId(planoRecurso1.getId());
        assertThat(planoRecurso1).isEqualTo(planoRecurso2);
        planoRecurso2.setId(2L);
        assertThat(planoRecurso1).isNotEqualTo(planoRecurso2);
        planoRecurso1.setId(null);
        assertThat(planoRecurso1).isNotEqualTo(planoRecurso2);
    }
}
