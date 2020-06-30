package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class RecursoTemplateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecursoTemplateDTO.class);
        RecursoTemplateDTO recursoTemplateDTO1 = new RecursoTemplateDTO();
        recursoTemplateDTO1.setId(1L);
        RecursoTemplateDTO recursoTemplateDTO2 = new RecursoTemplateDTO();
        assertThat(recursoTemplateDTO1).isNotEqualTo(recursoTemplateDTO2);
        recursoTemplateDTO2.setId(recursoTemplateDTO1.getId());
        assertThat(recursoTemplateDTO1).isEqualTo(recursoTemplateDTO2);
        recursoTemplateDTO2.setId(2L);
        assertThat(recursoTemplateDTO1).isNotEqualTo(recursoTemplateDTO2);
        recursoTemplateDTO1.setId(null);
        assertThat(recursoTemplateDTO1).isNotEqualTo(recursoTemplateDTO2);
    }
}
