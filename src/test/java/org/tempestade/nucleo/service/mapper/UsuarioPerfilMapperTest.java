package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioPerfilMapperTest {

    private UsuarioPerfilMapper usuarioPerfilMapper;

    @BeforeEach
    public void setUp() {
        usuarioPerfilMapper = new UsuarioPerfilMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(usuarioPerfilMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(usuarioPerfilMapper.fromId(null)).isNull();
    }
}
