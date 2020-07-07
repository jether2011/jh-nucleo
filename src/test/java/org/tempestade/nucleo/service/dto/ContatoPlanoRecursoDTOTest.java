package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class ContatoPlanoRecursoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContatoPlanoRecursoDTO.class);
        ContatoPlanoRecursoDTO contatoPlanoRecursoDTO1 = new ContatoPlanoRecursoDTO();
        contatoPlanoRecursoDTO1.setId(1L);
        ContatoPlanoRecursoDTO contatoPlanoRecursoDTO2 = new ContatoPlanoRecursoDTO();
        assertThat(contatoPlanoRecursoDTO1).isNotEqualTo(contatoPlanoRecursoDTO2);
        contatoPlanoRecursoDTO2.setId(contatoPlanoRecursoDTO1.getId());
        assertThat(contatoPlanoRecursoDTO1).isEqualTo(contatoPlanoRecursoDTO2);
        contatoPlanoRecursoDTO2.setId(2L);
        assertThat(contatoPlanoRecursoDTO1).isNotEqualTo(contatoPlanoRecursoDTO2);
        contatoPlanoRecursoDTO1.setId(null);
        assertThat(contatoPlanoRecursoDTO1).isNotEqualTo(contatoPlanoRecursoDTO2);
    }
}
