package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class VoceSabiaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoceSabia.class);
        VoceSabia voceSabia1 = new VoceSabia();
        voceSabia1.setId(1L);
        VoceSabia voceSabia2 = new VoceSabia();
        voceSabia2.setId(voceSabia1.getId());
        assertThat(voceSabia1).isEqualTo(voceSabia2);
        voceSabia2.setId(2L);
        assertThat(voceSabia1).isNotEqualTo(voceSabia2);
        voceSabia1.setId(null);
        assertThat(voceSabia1).isNotEqualTo(voceSabia2);
    }
}
