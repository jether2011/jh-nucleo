package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JornadaTrabalhoMapperTest {

    private JornadaTrabalhoMapper jornadaTrabalhoMapper;

    @BeforeEach
    public void setUp() {
        jornadaTrabalhoMapper = new JornadaTrabalhoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(jornadaTrabalhoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jornadaTrabalhoMapper.fromId(null)).isNull();
    }
}
