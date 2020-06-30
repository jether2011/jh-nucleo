package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class RedeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RedeDTO.class);
        RedeDTO redeDTO1 = new RedeDTO();
        redeDTO1.setId(1L);
        RedeDTO redeDTO2 = new RedeDTO();
        assertThat(redeDTO1).isNotEqualTo(redeDTO2);
        redeDTO2.setId(redeDTO1.getId());
        assertThat(redeDTO1).isEqualTo(redeDTO2);
        redeDTO2.setId(2L);
        assertThat(redeDTO1).isNotEqualTo(redeDTO2);
        redeDTO1.setId(null);
        assertThat(redeDTO1).isNotEqualTo(redeDTO2);
    }
}
