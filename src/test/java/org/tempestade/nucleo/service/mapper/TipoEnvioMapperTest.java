package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoEnvioMapperTest {

    private TipoEnvioMapper tipoEnvioMapper;

    @BeforeEach
    public void setUp() {
        tipoEnvioMapper = new TipoEnvioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoEnvioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoEnvioMapper.fromId(null)).isNull();
    }
}
