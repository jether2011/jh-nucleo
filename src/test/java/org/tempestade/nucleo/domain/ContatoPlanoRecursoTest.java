package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class ContatoPlanoRecursoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContatoPlanoRecurso.class);
        ContatoPlanoRecurso contatoPlanoRecurso1 = new ContatoPlanoRecurso();
        contatoPlanoRecurso1.setId(1L);
        ContatoPlanoRecurso contatoPlanoRecurso2 = new ContatoPlanoRecurso();
        contatoPlanoRecurso2.setId(contatoPlanoRecurso1.getId());
        assertThat(contatoPlanoRecurso1).isEqualTo(contatoPlanoRecurso2);
        contatoPlanoRecurso2.setId(2L);
        assertThat(contatoPlanoRecurso1).isNotEqualTo(contatoPlanoRecurso2);
        contatoPlanoRecurso1.setId(null);
        assertThat(contatoPlanoRecurso1).isNotEqualTo(contatoPlanoRecurso2);
    }
}
