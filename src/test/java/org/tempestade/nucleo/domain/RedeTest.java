package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class RedeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rede.class);
        Rede rede1 = new Rede();
        rede1.setId(1L);
        Rede rede2 = new Rede();
        rede2.setId(rede1.getId());
        assertThat(rede1).isEqualTo(rede2);
        rede2.setId(2L);
        assertThat(rede1).isNotEqualTo(rede2);
        rede1.setId(null);
        assertThat(rede1).isNotEqualTo(rede2);
    }
}
