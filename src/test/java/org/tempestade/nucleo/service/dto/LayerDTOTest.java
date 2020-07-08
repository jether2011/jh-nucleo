package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class LayerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LayerDTO.class);
        LayerDTO layerDTO1 = new LayerDTO();
        layerDTO1.setId(1L);
        LayerDTO layerDTO2 = new LayerDTO();
        assertThat(layerDTO1).isNotEqualTo(layerDTO2);
        layerDTO2.setId(layerDTO1.getId());
        assertThat(layerDTO1).isEqualTo(layerDTO2);
        layerDTO2.setId(2L);
        assertThat(layerDTO1).isNotEqualTo(layerDTO2);
        layerDTO1.setId(null);
        assertThat(layerDTO1).isNotEqualTo(layerDTO2);
    }
}
