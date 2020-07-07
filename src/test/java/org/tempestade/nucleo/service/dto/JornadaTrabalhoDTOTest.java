package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class JornadaTrabalhoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JornadaTrabalhoDTO.class);
        JornadaTrabalhoDTO jornadaTrabalhoDTO1 = new JornadaTrabalhoDTO();
        jornadaTrabalhoDTO1.setId(1L);
        JornadaTrabalhoDTO jornadaTrabalhoDTO2 = new JornadaTrabalhoDTO();
        assertThat(jornadaTrabalhoDTO1).isNotEqualTo(jornadaTrabalhoDTO2);
        jornadaTrabalhoDTO2.setId(jornadaTrabalhoDTO1.getId());
        assertThat(jornadaTrabalhoDTO1).isEqualTo(jornadaTrabalhoDTO2);
        jornadaTrabalhoDTO2.setId(2L);
        assertThat(jornadaTrabalhoDTO1).isNotEqualTo(jornadaTrabalhoDTO2);
        jornadaTrabalhoDTO1.setId(null);
        assertThat(jornadaTrabalhoDTO1).isNotEqualTo(jornadaTrabalhoDTO2);
    }
}
