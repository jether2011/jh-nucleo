package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BoletimPrevObsMapperTest {

    private BoletimPrevObsMapper boletimPrevObsMapper;

    @BeforeEach
    public void setUp() {
        boletimPrevObsMapper = new BoletimPrevObsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(boletimPrevObsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(boletimPrevObsMapper.fromId(null)).isNull();
    }
}
