package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AvisoTipoMapperTest {

    private AvisoTipoMapper avisoTipoMapper;

    @BeforeEach
    public void setUp() {
        avisoTipoMapper = new AvisoTipoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(avisoTipoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(avisoTipoMapper.fromId(null)).isNull();
    }
}
