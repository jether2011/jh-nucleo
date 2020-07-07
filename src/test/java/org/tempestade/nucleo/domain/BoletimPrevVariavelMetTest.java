package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class BoletimPrevVariavelMetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoletimPrevVariavelMet.class);
        BoletimPrevVariavelMet boletimPrevVariavelMet1 = new BoletimPrevVariavelMet();
        boletimPrevVariavelMet1.setId(1L);
        BoletimPrevVariavelMet boletimPrevVariavelMet2 = new BoletimPrevVariavelMet();
        boletimPrevVariavelMet2.setId(boletimPrevVariavelMet1.getId());
        assertThat(boletimPrevVariavelMet1).isEqualTo(boletimPrevVariavelMet2);
        boletimPrevVariavelMet2.setId(2L);
        assertThat(boletimPrevVariavelMet1).isNotEqualTo(boletimPrevVariavelMet2);
        boletimPrevVariavelMet1.setId(null);
        assertThat(boletimPrevVariavelMet1).isNotEqualTo(boletimPrevVariavelMet2);
    }
}
