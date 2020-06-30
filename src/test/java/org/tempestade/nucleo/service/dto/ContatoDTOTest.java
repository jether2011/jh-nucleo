package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class ContatoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContatoDTO.class);
        ContatoDTO contatoDTO1 = new ContatoDTO();
        contatoDTO1.setId(1L);
        ContatoDTO contatoDTO2 = new ContatoDTO();
        assertThat(contatoDTO1).isNotEqualTo(contatoDTO2);
        contatoDTO2.setId(contatoDTO1.getId());
        assertThat(contatoDTO1).isEqualTo(contatoDTO2);
        contatoDTO2.setId(2L);
        assertThat(contatoDTO1).isNotEqualTo(contatoDTO2);
        contatoDTO1.setId(null);
        assertThat(contatoDTO1).isNotEqualTo(contatoDTO2);
    }
}
