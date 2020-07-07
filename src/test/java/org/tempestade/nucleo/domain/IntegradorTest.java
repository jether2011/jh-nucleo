package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class IntegradorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Integrador.class);
        Integrador integrador1 = new Integrador();
        integrador1.setId(1L);
        Integrador integrador2 = new Integrador();
        integrador2.setId(integrador1.getId());
        assertThat(integrador1).isEqualTo(integrador2);
        integrador2.setId(2L);
        assertThat(integrador1).isNotEqualTo(integrador2);
        integrador1.setId(null);
        assertThat(integrador1).isNotEqualTo(integrador2);
    }
}
