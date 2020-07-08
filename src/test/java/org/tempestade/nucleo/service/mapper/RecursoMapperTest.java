package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecursoMapperTest {

    private RecursoMapper recursoMapper;

    @BeforeEach
    public void setUp() {
        recursoMapper = new RecursoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(recursoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recursoMapper.fromId(null)).isNull();
    }
}
