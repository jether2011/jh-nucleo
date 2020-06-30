package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PlanoRedeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoRedeDTO.class);
        PlanoRedeDTO planoRedeDTO1 = new PlanoRedeDTO();
        planoRedeDTO1.setId(1L);
        PlanoRedeDTO planoRedeDTO2 = new PlanoRedeDTO();
        assertThat(planoRedeDTO1).isNotEqualTo(planoRedeDTO2);
        planoRedeDTO2.setId(planoRedeDTO1.getId());
        assertThat(planoRedeDTO1).isEqualTo(planoRedeDTO2);
        planoRedeDTO2.setId(2L);
        assertThat(planoRedeDTO1).isNotEqualTo(planoRedeDTO2);
        planoRedeDTO1.setId(null);
        assertThat(planoRedeDTO1).isNotEqualTo(planoRedeDTO2);
    }
}
