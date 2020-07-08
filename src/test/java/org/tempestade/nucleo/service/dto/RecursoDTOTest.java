package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class RecursoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecursoDTO.class);
        RecursoDTO recursoDTO1 = new RecursoDTO();
        recursoDTO1.setId(1L);
        RecursoDTO recursoDTO2 = new RecursoDTO();
        assertThat(recursoDTO1).isNotEqualTo(recursoDTO2);
        recursoDTO2.setId(recursoDTO1.getId());
        assertThat(recursoDTO1).isEqualTo(recursoDTO2);
        recursoDTO2.setId(2L);
        assertThat(recursoDTO1).isNotEqualTo(recursoDTO2);
        recursoDTO1.setId(null);
        assertThat(recursoDTO1).isNotEqualTo(recursoDTO2);
    }
}
