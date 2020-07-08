package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanoUsuarioMapperTest {

    private PlanoUsuarioMapper planoUsuarioMapper;

    @BeforeEach
    public void setUp() {
        planoUsuarioMapper = new PlanoUsuarioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planoUsuarioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planoUsuarioMapper.fromId(null)).isNull();
    }
}
