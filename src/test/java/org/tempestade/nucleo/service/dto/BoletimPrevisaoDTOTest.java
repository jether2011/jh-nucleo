package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class BoletimPrevisaoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoletimPrevisaoDTO.class);
        BoletimPrevisaoDTO boletimPrevisaoDTO1 = new BoletimPrevisaoDTO();
        boletimPrevisaoDTO1.setId(1L);
        BoletimPrevisaoDTO boletimPrevisaoDTO2 = new BoletimPrevisaoDTO();
        assertThat(boletimPrevisaoDTO1).isNotEqualTo(boletimPrevisaoDTO2);
        boletimPrevisaoDTO2.setId(boletimPrevisaoDTO1.getId());
        assertThat(boletimPrevisaoDTO1).isEqualTo(boletimPrevisaoDTO2);
        boletimPrevisaoDTO2.setId(2L);
        assertThat(boletimPrevisaoDTO1).isNotEqualTo(boletimPrevisaoDTO2);
        boletimPrevisaoDTO1.setId(null);
        assertThat(boletimPrevisaoDTO1).isNotEqualTo(boletimPrevisaoDTO2);
    }
}
