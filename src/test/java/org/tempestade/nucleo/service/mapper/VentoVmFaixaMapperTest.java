package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VentoVmFaixaMapperTest {

    private VentoVmFaixaMapper ventoVmFaixaMapper;

    @BeforeEach
    public void setUp() {
        ventoVmFaixaMapper = new VentoVmFaixaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ventoVmFaixaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ventoVmFaixaMapper.fromId(null)).isNull();
    }
}
