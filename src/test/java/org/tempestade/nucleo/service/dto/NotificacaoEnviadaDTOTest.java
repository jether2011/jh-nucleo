package org.tempestade.nucleo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class NotificacaoEnviadaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificacaoEnviadaDTO.class);
        NotificacaoEnviadaDTO notificacaoEnviadaDTO1 = new NotificacaoEnviadaDTO();
        notificacaoEnviadaDTO1.setId(1L);
        NotificacaoEnviadaDTO notificacaoEnviadaDTO2 = new NotificacaoEnviadaDTO();
        assertThat(notificacaoEnviadaDTO1).isNotEqualTo(notificacaoEnviadaDTO2);
        notificacaoEnviadaDTO2.setId(notificacaoEnviadaDTO1.getId());
        assertThat(notificacaoEnviadaDTO1).isEqualTo(notificacaoEnviadaDTO2);
        notificacaoEnviadaDTO2.setId(2L);
        assertThat(notificacaoEnviadaDTO1).isNotEqualTo(notificacaoEnviadaDTO2);
        notificacaoEnviadaDTO1.setId(null);
        assertThat(notificacaoEnviadaDTO1).isNotEqualTo(notificacaoEnviadaDTO2);
    }
}
