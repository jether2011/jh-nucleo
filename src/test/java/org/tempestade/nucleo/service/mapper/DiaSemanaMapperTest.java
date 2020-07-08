package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DiaSemanaMapperTest {

    private DiaSemanaMapper diaSemanaMapper;

    @BeforeEach
    public void setUp() {
        diaSemanaMapper = new DiaSemanaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(diaSemanaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(diaSemanaMapper.fromId(null)).isNull();
    }
}
