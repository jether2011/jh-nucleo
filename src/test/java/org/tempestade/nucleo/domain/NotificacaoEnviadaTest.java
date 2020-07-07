package org.tempestade.nucleo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.tempestade.nucleo.web.rest.TestUtil;

public class NotificacaoEnviadaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificacaoEnviada.class);
        NotificacaoEnviada notificacaoEnviada1 = new NotificacaoEnviada();
        notificacaoEnviada1.setId(1L);
        NotificacaoEnviada notificacaoEnviada2 = new NotificacaoEnviada();
        notificacaoEnviada2.setId(notificacaoEnviada1.getId());
        assertThat(notificacaoEnviada1).isEqualTo(notificacaoEnviada2);
        notificacaoEnviada2.setId(2L);
        assertThat(notificacaoEnviada1).isNotEqualTo(notificacaoEnviada2);
        notificacaoEnviada1.setId(null);
        assertThat(notificacaoEnviada1).isNotEqualTo(notificacaoEnviada2);
    }
}
