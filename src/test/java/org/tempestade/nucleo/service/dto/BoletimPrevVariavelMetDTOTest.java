package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class BoletimPrevVariavelMetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoletimPrevVariavelMetDTO.class);
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO1 = new BoletimPrevVariavelMetDTO();
        boletimPrevVariavelMetDTO1.setId(1L);
        BoletimPrevVariavelMetDTO boletimPrevVariavelMetDTO2 = new BoletimPrevVariavelMetDTO();
        assertThat(boletimPrevVariavelMetDTO1).isNotEqualTo(boletimPrevVariavelMetDTO2);
        boletimPrevVariavelMetDTO2.setId(boletimPrevVariavelMetDTO1.getId());
        assertThat(boletimPrevVariavelMetDTO1).isEqualTo(boletimPrevVariavelMetDTO2);
        boletimPrevVariavelMetDTO2.setId(2L);
        assertThat(boletimPrevVariavelMetDTO1).isNotEqualTo(boletimPrevVariavelMetDTO2);
        boletimPrevVariavelMetDTO1.setId(null);
        assertThat(boletimPrevVariavelMetDTO1).isNotEqualTo(boletimPrevVariavelMetDTO2);
    }
}
