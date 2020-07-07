package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class PerfilDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilDTO.class);
        PerfilDTO perfilDTO1 = new PerfilDTO();
        perfilDTO1.setId(1L);
        PerfilDTO perfilDTO2 = new PerfilDTO();
        assertThat(perfilDTO1).isNotEqualTo(perfilDTO2);
        perfilDTO2.setId(perfilDTO1.getId());
        assertThat(perfilDTO1).isEqualTo(perfilDTO2);
        perfilDTO2.setId(2L);
        assertThat(perfilDTO1).isNotEqualTo(perfilDTO2);
        perfilDTO1.setId(null);
        assertThat(perfilDTO1).isNotEqualTo(perfilDTO2);
    }
}
