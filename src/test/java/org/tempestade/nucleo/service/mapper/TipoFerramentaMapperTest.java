package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoFerramentaMapperTest {

    private TipoFerramentaMapper tipoFerramentaMapper;

    @BeforeEach
    public void setUp() {
        tipoFerramentaMapper = new TipoFerramentaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoFerramentaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoFerramentaMapper.fromId(null)).isNull();
    }
}
