package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanoRecursoMapperTest {

    private PlanoRecursoMapper planoRecursoMapper;

    @BeforeEach
    public void setUp() {
        planoRecursoMapper = new PlanoRecursoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planoRecursoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planoRecursoMapper.fromId(null)).isNull();
    }
}
