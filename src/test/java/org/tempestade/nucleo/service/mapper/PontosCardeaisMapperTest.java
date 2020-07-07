package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PontosCardeaisMapperTest {

    private PontosCardeaisMapper pontosCardeaisMapper;

    @BeforeEach
    public void setUp() {
        pontosCardeaisMapper = new PontosCardeaisMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pontosCardeaisMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pontosCardeaisMapper.fromId(null)).isNull();
    }
}
