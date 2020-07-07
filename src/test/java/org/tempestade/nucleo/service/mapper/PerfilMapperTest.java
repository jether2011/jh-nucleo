package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PerfilMapperTest {

    private PerfilMapper perfilMapper;

    @BeforeEach
    public void setUp() {
        perfilMapper = new PerfilMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(perfilMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(perfilMapper.fromId(null)).isNull();
    }
}
