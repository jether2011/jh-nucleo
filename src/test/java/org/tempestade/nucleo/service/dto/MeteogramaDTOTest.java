package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class MeteogramaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeteogramaDTO.class);
        MeteogramaDTO meteogramaDTO1 = new MeteogramaDTO();
        meteogramaDTO1.setId(1L);
        MeteogramaDTO meteogramaDTO2 = new MeteogramaDTO();
        assertThat(meteogramaDTO1).isNotEqualTo(meteogramaDTO2);
        meteogramaDTO2.setId(meteogramaDTO1.getId());
        assertThat(meteogramaDTO1).isEqualTo(meteogramaDTO2);
        meteogramaDTO2.setId(2L);
        assertThat(meteogramaDTO1).isNotEqualTo(meteogramaDTO2);
        meteogramaDTO1.setId(null);
        assertThat(meteogramaDTO1).isNotEqualTo(meteogramaDTO2);
    }
}
