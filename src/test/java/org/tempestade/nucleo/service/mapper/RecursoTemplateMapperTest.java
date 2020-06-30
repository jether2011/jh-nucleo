package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RecursoTemplateMapperTest {

    private RecursoTemplateMapper recursoTemplateMapper;

    @BeforeEach
    public void setUp() {
        recursoTemplateMapper = new RecursoTemplateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(recursoTemplateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(recursoTemplateMapper.fromId(null)).isNull();
    }
}
