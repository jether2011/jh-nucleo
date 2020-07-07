package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoStatusDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoStatusDTO.class);
        PlanoStatusDTO planoStatusDTO1 = new PlanoStatusDTO();
        planoStatusDTO1.setId(1L);
        PlanoStatusDTO planoStatusDTO2 = new PlanoStatusDTO();
        assertThat(planoStatusDTO1).isNotEqualTo(planoStatusDTO2);
        planoStatusDTO2.setId(planoStatusDTO1.getId());
        assertThat(planoStatusDTO1).isEqualTo(planoStatusDTO2);
        planoStatusDTO2.setId(2L);
        assertThat(planoStatusDTO1).isNotEqualTo(planoStatusDTO2);
        planoStatusDTO1.setId(null);
        assertThat(planoStatusDTO1).isNotEqualTo(planoStatusDTO2);
    }
}
