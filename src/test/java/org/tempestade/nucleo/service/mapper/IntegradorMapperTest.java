package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IntegradorMapperTest {

    private IntegradorMapper integradorMapper;

    @BeforeEach
    public void setUp() {
        integradorMapper = new IntegradorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(integradorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(integradorMapper.fromId(null)).isNull();
    }
}
