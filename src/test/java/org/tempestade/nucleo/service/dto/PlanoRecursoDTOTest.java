package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoRecursoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoRecursoDTO.class);
        PlanoRecursoDTO planoRecursoDTO1 = new PlanoRecursoDTO();
        planoRecursoDTO1.setId(1L);
        PlanoRecursoDTO planoRecursoDTO2 = new PlanoRecursoDTO();
        assertThat(planoRecursoDTO1).isNotEqualTo(planoRecursoDTO2);
        planoRecursoDTO2.setId(planoRecursoDTO1.getId());
        assertThat(planoRecursoDTO1).isEqualTo(planoRecursoDTO2);
        planoRecursoDTO2.setId(2L);
        assertThat(planoRecursoDTO1).isNotEqualTo(planoRecursoDTO2);
        planoRecursoDTO1.setId(null);
        assertThat(planoRecursoDTO1).isNotEqualTo(planoRecursoDTO2);
    }
}
