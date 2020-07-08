package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LayerMapperTest {

    private LayerMapper layerMapper;

    @BeforeEach
    public void setUp() {
        layerMapper = new LayerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(layerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(layerMapper.fromId(null)).isNull();
    }
}
