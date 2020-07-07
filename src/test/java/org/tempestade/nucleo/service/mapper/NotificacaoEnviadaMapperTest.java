package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NotificacaoEnviadaMapperTest {

    private NotificacaoEnviadaMapper notificacaoEnviadaMapper;

    @BeforeEach
    public void setUp() {
        notificacaoEnviadaMapper = new NotificacaoEnviadaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(notificacaoEnviadaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(notificacaoEnviadaMapper.fromId(null)).isNull();
    }
}
