package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VariavelMeteorologicaMapperTest {

    private VariavelMeteorologicaMapper variavelMeteorologicaMapper;

    @BeforeEach
    public void setUp() {
        variavelMeteorologicaMapper = new VariavelMeteorologicaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(variavelMeteorologicaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(variavelMeteorologicaMapper.fromId(null)).isNull();
    }
}
