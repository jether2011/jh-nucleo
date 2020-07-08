package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanoRecursoTipoEnvioMapperTest {

    private PlanoRecursoTipoEnvioMapper planoRecursoTipoEnvioMapper;

    @BeforeEach
    public void setUp() {
        planoRecursoTipoEnvioMapper = new PlanoRecursoTipoEnvioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planoRecursoTipoEnvioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planoRecursoTipoEnvioMapper.fromId(null)).isNull();
    }
}
