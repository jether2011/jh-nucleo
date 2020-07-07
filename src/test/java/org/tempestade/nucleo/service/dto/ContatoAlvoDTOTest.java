package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class ContatoAlvoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContatoAlvoDTO.class);
        ContatoAlvoDTO contatoAlvoDTO1 = new ContatoAlvoDTO();
        contatoAlvoDTO1.setId(1L);
        ContatoAlvoDTO contatoAlvoDTO2 = new ContatoAlvoDTO();
        assertThat(contatoAlvoDTO1).isNotEqualTo(contatoAlvoDTO2);
        contatoAlvoDTO2.setId(contatoAlvoDTO1.getId());
        assertThat(contatoAlvoDTO1).isEqualTo(contatoAlvoDTO2);
        contatoAlvoDTO2.setId(2L);
        assertThat(contatoAlvoDTO1).isNotEqualTo(contatoAlvoDTO2);
        contatoAlvoDTO1.setId(null);
        assertThat(contatoAlvoDTO1).isNotEqualTo(contatoAlvoDTO2);
    }
}
