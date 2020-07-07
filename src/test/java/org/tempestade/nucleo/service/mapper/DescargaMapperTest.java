package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DescargaMapperTest {

    private DescargaMapper descargaMapper;

    @BeforeEach
    public void setUp() {
        descargaMapper = new DescargaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(descargaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(descargaMapper.fromId(null)).isNull();
    }
}
