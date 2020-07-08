package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CondicaoTempoMapperTest {

    private CondicaoTempoMapper condicaoTempoMapper;

    @BeforeEach
    public void setUp() {
        condicaoTempoMapper = new CondicaoTempoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(condicaoTempoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(condicaoTempoMapper.fromId(null)).isNull();
    }
}
