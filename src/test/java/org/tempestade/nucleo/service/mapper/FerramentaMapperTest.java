package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FerramentaMapperTest {

    private FerramentaMapper ferramentaMapper;

    @BeforeEach
    public void setUp() {
        ferramentaMapper = new FerramentaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ferramentaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ferramentaMapper.fromId(null)).isNull();
    }
}
