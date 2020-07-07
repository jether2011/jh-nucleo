package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoEnvioIntegradorMapperTest {

    private TipoEnvioIntegradorMapper tipoEnvioIntegradorMapper;

    @BeforeEach
    public void setUp() {
        tipoEnvioIntegradorMapper = new TipoEnvioIntegradorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoEnvioIntegradorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoEnvioIntegradorMapper.fromId(null)).isNull();
    }
}
