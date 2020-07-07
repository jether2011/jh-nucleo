package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PrevisaoMapperTest {

    private PrevisaoMapper previsaoMapper;

    @BeforeEach
    public void setUp() {
        previsaoMapper = new PrevisaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(previsaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(previsaoMapper.fromId(null)).isNull();
    }
}
