package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class ContatoAlvoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContatoAlvo.class);
        ContatoAlvo contatoAlvo1 = new ContatoAlvo();
        contatoAlvo1.setId(1L);
        ContatoAlvo contatoAlvo2 = new ContatoAlvo();
        contatoAlvo2.setId(contatoAlvo1.getId());
        assertThat(contatoAlvo1).isEqualTo(contatoAlvo2);
        contatoAlvo2.setId(2L);
        assertThat(contatoAlvo1).isNotEqualTo(contatoAlvo2);
        contatoAlvo1.setId(null);
        assertThat(contatoAlvo1).isNotEqualTo(contatoAlvo2);
    }
}
