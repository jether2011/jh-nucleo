package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IntensidadeChuvaMapperTest {

    private IntensidadeChuvaMapper intensidadeChuvaMapper;

    @BeforeEach
    public void setUp() {
        intensidadeChuvaMapper = new IntensidadeChuvaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(intensidadeChuvaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(intensidadeChuvaMapper.fromId(null)).isNull();
    }
}
