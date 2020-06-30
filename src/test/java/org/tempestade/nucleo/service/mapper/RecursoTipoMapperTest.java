package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecursoTipoMapperTest {

    private RecursoTipoMapper recursoTipoMapper;

    @BeforeEach
    public void setUp() {
        recursoTipoMapper = new RecursoTipoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(recursoTipoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recursoTipoMapper.fromId(null)).isNull();
    }
}
