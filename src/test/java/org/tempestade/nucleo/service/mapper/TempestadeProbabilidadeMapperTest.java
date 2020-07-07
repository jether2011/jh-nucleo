package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TempestadeProbabilidadeMapperTest {

    private TempestadeProbabilidadeMapper tempestadeProbabilidadeMapper;

    @BeforeEach
    public void setUp() {
        tempestadeProbabilidadeMapper = new TempestadeProbabilidadeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tempestadeProbabilidadeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tempestadeProbabilidadeMapper.fromId(null)).isNull();
    }
}
