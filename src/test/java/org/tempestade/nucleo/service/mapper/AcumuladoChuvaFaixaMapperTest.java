package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AcumuladoChuvaFaixaMapperTest {

    private AcumuladoChuvaFaixaMapper acumuladoChuvaFaixaMapper;

    @BeforeEach
    public void setUp() {
        acumuladoChuvaFaixaMapper = new AcumuladoChuvaFaixaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(acumuladoChuvaFaixaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(acumuladoChuvaFaixaMapper.fromId(null)).isNull();
    }
}
