package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class IntensidadeChuvaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntensidadeChuvaDTO.class);
        IntensidadeChuvaDTO intensidadeChuvaDTO1 = new IntensidadeChuvaDTO();
        intensidadeChuvaDTO1.setId(1L);
        IntensidadeChuvaDTO intensidadeChuvaDTO2 = new IntensidadeChuvaDTO();
        assertThat(intensidadeChuvaDTO1).isNotEqualTo(intensidadeChuvaDTO2);
        intensidadeChuvaDTO2.setId(intensidadeChuvaDTO1.getId());
        assertThat(intensidadeChuvaDTO1).isEqualTo(intensidadeChuvaDTO2);
        intensidadeChuvaDTO2.setId(2L);
        assertThat(intensidadeChuvaDTO1).isNotEqualTo(intensidadeChuvaDTO2);
        intensidadeChuvaDTO1.setId(null);
        assertThat(intensidadeChuvaDTO1).isNotEqualTo(intensidadeChuvaDTO2);
    }
}
