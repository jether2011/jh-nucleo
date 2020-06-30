package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AvisoMeteorologicoMapperTest {

    private AvisoMeteorologicoMapper avisoMeteorologicoMapper;

    @BeforeEach
    public void setUp() {
        avisoMeteorologicoMapper = new AvisoMeteorologicoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(avisoMeteorologicoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(avisoMeteorologicoMapper.fromId(null)).isNull();
    }
}
