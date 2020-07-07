package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AvisoMapperTest {

    private AvisoMapper avisoMapper;

    @BeforeEach
    public void setUp() {
        avisoMapper = new AvisoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(avisoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(avisoMapper.fromId(null)).isNull();
    }
}
