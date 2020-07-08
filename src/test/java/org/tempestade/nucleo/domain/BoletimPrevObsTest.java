package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class BoletimPrevObsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoletimPrevObs.class);
        BoletimPrevObs boletimPrevObs1 = new BoletimPrevObs();
        boletimPrevObs1.setId(1L);
        BoletimPrevObs boletimPrevObs2 = new BoletimPrevObs();
        boletimPrevObs2.setId(boletimPrevObs1.getId());
        assertThat(boletimPrevObs1).isEqualTo(boletimPrevObs2);
        boletimPrevObs2.setId(2L);
        assertThat(boletimPrevObs1).isNotEqualTo(boletimPrevObs2);
        boletimPrevObs1.setId(null);
        assertThat(boletimPrevObs1).isNotEqualTo(boletimPrevObs2);
    }
}
