package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class BoletimDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoletimDTO.class);
        BoletimDTO boletimDTO1 = new BoletimDTO();
        boletimDTO1.setId(1L);
        BoletimDTO boletimDTO2 = new BoletimDTO();
        assertThat(boletimDTO1).isNotEqualTo(boletimDTO2);
        boletimDTO2.setId(boletimDTO1.getId());
        assertThat(boletimDTO1).isEqualTo(boletimDTO2);
        boletimDTO2.setId(2L);
        assertThat(boletimDTO1).isNotEqualTo(boletimDTO2);
        boletimDTO1.setId(null);
        assertThat(boletimDTO1).isNotEqualTo(boletimDTO2);
    }
}
