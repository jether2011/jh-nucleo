package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class InformativoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformativoDTO.class);
        InformativoDTO informativoDTO1 = new InformativoDTO();
        informativoDTO1.setId(1L);
        InformativoDTO informativoDTO2 = new InformativoDTO();
        assertThat(informativoDTO1).isNotEqualTo(informativoDTO2);
        informativoDTO2.setId(informativoDTO1.getId());
        assertThat(informativoDTO1).isEqualTo(informativoDTO2);
        informativoDTO2.setId(2L);
        assertThat(informativoDTO1).isNotEqualTo(informativoDTO2);
        informativoDTO1.setId(null);
        assertThat(informativoDTO1).isNotEqualTo(informativoDTO2);
    }
}
