package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class RecursoTemplateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecursoTemplate.class);
        RecursoTemplate recursoTemplate1 = new RecursoTemplate();
        recursoTemplate1.setId(1L);
        RecursoTemplate recursoTemplate2 = new RecursoTemplate();
        recursoTemplate2.setId(recursoTemplate1.getId());
        assertThat(recursoTemplate1).isEqualTo(recursoTemplate2);
        recursoTemplate2.setId(2L);
        assertThat(recursoTemplate1).isNotEqualTo(recursoTemplate2);
        recursoTemplate1.setId(null);
        assertThat(recursoTemplate1).isNotEqualTo(recursoTemplate2);
    }
}
