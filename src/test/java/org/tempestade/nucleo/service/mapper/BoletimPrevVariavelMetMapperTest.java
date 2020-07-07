package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BoletimPrevVariavelMetMapperTest {

    private BoletimPrevVariavelMetMapper boletimPrevVariavelMetMapper;

    @BeforeEach
    public void setUp() {
        boletimPrevVariavelMetMapper = new BoletimPrevVariavelMetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(boletimPrevVariavelMetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(boletimPrevVariavelMetMapper.fromId(null)).isNull();
    }
}
