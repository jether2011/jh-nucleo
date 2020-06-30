package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class DescargaUnidadeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DescargaUnidadeDTO.class);
        DescargaUnidadeDTO descargaUnidadeDTO1 = new DescargaUnidadeDTO();
        descargaUnidadeDTO1.setId(1L);
        DescargaUnidadeDTO descargaUnidadeDTO2 = new DescargaUnidadeDTO();
        assertThat(descargaUnidadeDTO1).isNotEqualTo(descargaUnidadeDTO2);
        descargaUnidadeDTO2.setId(descargaUnidadeDTO1.getId());
        assertThat(descargaUnidadeDTO1).isEqualTo(descargaUnidadeDTO2);
        descargaUnidadeDTO2.setId(2L);
        assertThat(descargaUnidadeDTO1).isNotEqualTo(descargaUnidadeDTO2);
        descargaUnidadeDTO1.setId(null);
        assertThat(descargaUnidadeDTO1).isNotEqualTo(descargaUnidadeDTO2);
    }
}
