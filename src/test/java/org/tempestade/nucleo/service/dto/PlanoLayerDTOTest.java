package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoLayerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoLayerDTO.class);
        PlanoLayerDTO planoLayerDTO1 = new PlanoLayerDTO();
        planoLayerDTO1.setId(1L);
        PlanoLayerDTO planoLayerDTO2 = new PlanoLayerDTO();
        assertThat(planoLayerDTO1).isNotEqualTo(planoLayerDTO2);
        planoLayerDTO2.setId(planoLayerDTO1.getId());
        assertThat(planoLayerDTO1).isEqualTo(planoLayerDTO2);
        planoLayerDTO2.setId(2L);
        assertThat(planoLayerDTO1).isNotEqualTo(planoLayerDTO2);
        planoLayerDTO1.setId(null);
        assertThat(planoLayerDTO1).isNotEqualTo(planoLayerDTO2);
    }
}
