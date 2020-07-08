package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlvoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlvoDTO.class);
        AlvoDTO alvoDTO1 = new AlvoDTO();
        alvoDTO1.setId(1L);
        AlvoDTO alvoDTO2 = new AlvoDTO();
        assertThat(alvoDTO1).isNotEqualTo(alvoDTO2);
        alvoDTO2.setId(alvoDTO1.getId());
        assertThat(alvoDTO1).isEqualTo(alvoDTO2);
        alvoDTO2.setId(2L);
        assertThat(alvoDTO1).isNotEqualTo(alvoDTO2);
        alvoDTO1.setId(null);
        assertThat(alvoDTO1).isNotEqualTo(alvoDTO2);
    }
}
