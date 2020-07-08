package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class TempestadeNivelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TempestadeNivelDTO.class);
        TempestadeNivelDTO tempestadeNivelDTO1 = new TempestadeNivelDTO();
        tempestadeNivelDTO1.setId(1L);
        TempestadeNivelDTO tempestadeNivelDTO2 = new TempestadeNivelDTO();
        assertThat(tempestadeNivelDTO1).isNotEqualTo(tempestadeNivelDTO2);
        tempestadeNivelDTO2.setId(tempestadeNivelDTO1.getId());
        assertThat(tempestadeNivelDTO1).isEqualTo(tempestadeNivelDTO2);
        tempestadeNivelDTO2.setId(2L);
        assertThat(tempestadeNivelDTO1).isNotEqualTo(tempestadeNivelDTO2);
        tempestadeNivelDTO1.setId(null);
        assertThat(tempestadeNivelDTO1).isNotEqualTo(tempestadeNivelDTO2);
    }
}
