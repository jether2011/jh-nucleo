package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class AlvoBloqueioDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlvoBloqueioDTO.class);
        AlvoBloqueioDTO alvoBloqueioDTO1 = new AlvoBloqueioDTO();
        alvoBloqueioDTO1.setId(1L);
        AlvoBloqueioDTO alvoBloqueioDTO2 = new AlvoBloqueioDTO();
        assertThat(alvoBloqueioDTO1).isNotEqualTo(alvoBloqueioDTO2);
        alvoBloqueioDTO2.setId(alvoBloqueioDTO1.getId());
        assertThat(alvoBloqueioDTO1).isEqualTo(alvoBloqueioDTO2);
        alvoBloqueioDTO2.setId(2L);
        assertThat(alvoBloqueioDTO1).isNotEqualTo(alvoBloqueioDTO2);
        alvoBloqueioDTO1.setId(null);
        assertThat(alvoBloqueioDTO1).isNotEqualTo(alvoBloqueioDTO2);
    }
}
