package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class BoletimPrevObsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoletimPrevObsDTO.class);
        BoletimPrevObsDTO boletimPrevObsDTO1 = new BoletimPrevObsDTO();
        boletimPrevObsDTO1.setId(1L);
        BoletimPrevObsDTO boletimPrevObsDTO2 = new BoletimPrevObsDTO();
        assertThat(boletimPrevObsDTO1).isNotEqualTo(boletimPrevObsDTO2);
        boletimPrevObsDTO2.setId(boletimPrevObsDTO1.getId());
        assertThat(boletimPrevObsDTO1).isEqualTo(boletimPrevObsDTO2);
        boletimPrevObsDTO2.setId(2L);
        assertThat(boletimPrevObsDTO1).isNotEqualTo(boletimPrevObsDTO2);
        boletimPrevObsDTO1.setId(null);
        assertThat(boletimPrevObsDTO1).isNotEqualTo(boletimPrevObsDTO2);
    }
}
