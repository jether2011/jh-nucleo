package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanoStatusMapperTest {

    private PlanoStatusMapper planoStatusMapper;

    @BeforeEach
    public void setUp() {
        planoStatusMapper = new PlanoStatusMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planoStatusMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planoStatusMapper.fromId(null)).isNull();
    }
}
