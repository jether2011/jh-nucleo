package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class DiaSemanaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiaSemanaDTO.class);
        DiaSemanaDTO diaSemanaDTO1 = new DiaSemanaDTO();
        diaSemanaDTO1.setId(1L);
        DiaSemanaDTO diaSemanaDTO2 = new DiaSemanaDTO();
        assertThat(diaSemanaDTO1).isNotEqualTo(diaSemanaDTO2);
        diaSemanaDTO2.setId(diaSemanaDTO1.getId());
        assertThat(diaSemanaDTO1).isEqualTo(diaSemanaDTO2);
        diaSemanaDTO2.setId(2L);
        assertThat(diaSemanaDTO1).isNotEqualTo(diaSemanaDTO2);
        diaSemanaDTO1.setId(null);
        assertThat(diaSemanaDTO1).isNotEqualTo(diaSemanaDTO2);
    }
}
