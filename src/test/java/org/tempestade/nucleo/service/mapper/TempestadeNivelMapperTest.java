package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TempestadeNivelMapperTest {

    private TempestadeNivelMapper tempestadeNivelMapper;

    @BeforeEach
    public void setUp() {
        tempestadeNivelMapper = new TempestadeNivelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tempestadeNivelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tempestadeNivelMapper.fromId(null)).isNull();
    }
}
