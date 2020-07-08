package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class RecursoTipoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecursoTipo.class);
        RecursoTipo recursoTipo1 = new RecursoTipo();
        recursoTipo1.setId(1L);
        RecursoTipo recursoTipo2 = new RecursoTipo();
        recursoTipo2.setId(recursoTipo1.getId());
        assertThat(recursoTipo1).isEqualTo(recursoTipo2);
        recursoTipo2.setId(2L);
        assertThat(recursoTipo1).isNotEqualTo(recursoTipo2);
        recursoTipo1.setId(null);
        assertThat(recursoTipo1).isNotEqualTo(recursoTipo2);
    }
}
