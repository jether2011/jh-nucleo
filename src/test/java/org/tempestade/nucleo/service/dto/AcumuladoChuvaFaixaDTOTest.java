package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AcumuladoChuvaFaixaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcumuladoChuvaFaixaDTO.class);
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO1 = new AcumuladoChuvaFaixaDTO();
        acumuladoChuvaFaixaDTO1.setId(1L);
        AcumuladoChuvaFaixaDTO acumuladoChuvaFaixaDTO2 = new AcumuladoChuvaFaixaDTO();
        assertThat(acumuladoChuvaFaixaDTO1).isNotEqualTo(acumuladoChuvaFaixaDTO2);
        acumuladoChuvaFaixaDTO2.setId(acumuladoChuvaFaixaDTO1.getId());
        assertThat(acumuladoChuvaFaixaDTO1).isEqualTo(acumuladoChuvaFaixaDTO2);
        acumuladoChuvaFaixaDTO2.setId(2L);
        assertThat(acumuladoChuvaFaixaDTO1).isNotEqualTo(acumuladoChuvaFaixaDTO2);
        acumuladoChuvaFaixaDTO1.setId(null);
        assertThat(acumuladoChuvaFaixaDTO1).isNotEqualTo(acumuladoChuvaFaixaDTO2);
    }
}
