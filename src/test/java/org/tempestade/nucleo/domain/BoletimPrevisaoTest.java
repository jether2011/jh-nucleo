package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class BoletimPrevisaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoletimPrevisao.class);
        BoletimPrevisao boletimPrevisao1 = new BoletimPrevisao();
        boletimPrevisao1.setId(1L);
        BoletimPrevisao boletimPrevisao2 = new BoletimPrevisao();
        boletimPrevisao2.setId(boletimPrevisao1.getId());
        assertThat(boletimPrevisao1).isEqualTo(boletimPrevisao2);
        boletimPrevisao2.setId(2L);
        assertThat(boletimPrevisao1).isNotEqualTo(boletimPrevisao2);
        boletimPrevisao1.setId(null);
        assertThat(boletimPrevisao1).isNotEqualTo(boletimPrevisao2);
    }
}
