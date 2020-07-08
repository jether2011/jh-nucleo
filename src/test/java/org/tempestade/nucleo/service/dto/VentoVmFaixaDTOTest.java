package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class VentoVmFaixaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VentoVmFaixaDTO.class);
        VentoVmFaixaDTO ventoVmFaixaDTO1 = new VentoVmFaixaDTO();
        ventoVmFaixaDTO1.setId(1L);
        VentoVmFaixaDTO ventoVmFaixaDTO2 = new VentoVmFaixaDTO();
        assertThat(ventoVmFaixaDTO1).isNotEqualTo(ventoVmFaixaDTO2);
        ventoVmFaixaDTO2.setId(ventoVmFaixaDTO1.getId());
        assertThat(ventoVmFaixaDTO1).isEqualTo(ventoVmFaixaDTO2);
        ventoVmFaixaDTO2.setId(2L);
        assertThat(ventoVmFaixaDTO1).isNotEqualTo(ventoVmFaixaDTO2);
        ventoVmFaixaDTO1.setId(null);
        assertThat(ventoVmFaixaDTO1).isNotEqualTo(ventoVmFaixaDTO2);
    }
}
