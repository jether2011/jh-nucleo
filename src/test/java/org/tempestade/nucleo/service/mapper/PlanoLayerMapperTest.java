package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanoLayerMapperTest {

    private PlanoLayerMapper planoLayerMapper;

    @BeforeEach
    public void setUp() {
        planoLayerMapper = new PlanoLayerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planoLayerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planoLayerMapper.fromId(null)).isNull();
    }
}
