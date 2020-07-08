package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class RecursoTipoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecursoTipoDTO.class);
        RecursoTipoDTO recursoTipoDTO1 = new RecursoTipoDTO();
        recursoTipoDTO1.setId(1L);
        RecursoTipoDTO recursoTipoDTO2 = new RecursoTipoDTO();
        assertThat(recursoTipoDTO1).isNotEqualTo(recursoTipoDTO2);
        recursoTipoDTO2.setId(recursoTipoDTO1.getId());
        assertThat(recursoTipoDTO1).isEqualTo(recursoTipoDTO2);
        recursoTipoDTO2.setId(2L);
        assertThat(recursoTipoDTO1).isNotEqualTo(recursoTipoDTO2);
        recursoTipoDTO1.setId(null);
        assertThat(recursoTipoDTO1).isNotEqualTo(recursoTipoDTO2);
    }
}
