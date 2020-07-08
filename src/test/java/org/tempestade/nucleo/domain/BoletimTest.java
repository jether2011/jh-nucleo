package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class BoletimTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Boletim.class);
        Boletim boletim1 = new Boletim();
        boletim1.setId(1L);
        Boletim boletim2 = new Boletim();
        boletim2.setId(boletim1.getId());
        assertThat(boletim1).isEqualTo(boletim2);
        boletim2.setId(2L);
        assertThat(boletim1).isNotEqualTo(boletim2);
        boletim1.setId(null);
        assertThat(boletim1).isNotEqualTo(boletim2);
    }
}
