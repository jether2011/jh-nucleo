package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DescargaTipoMapperTest {

    private DescargaTipoMapper descargaTipoMapper;

    @BeforeEach
    public void setUp() {
        descargaTipoMapper = new DescargaTipoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(descargaTipoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(descargaTipoMapper.fromId(null)).isNull();
    }
}
