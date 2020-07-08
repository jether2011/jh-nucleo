package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BoletimMapperTest {

    private BoletimMapper boletimMapper;

    @BeforeEach
    public void setUp() {
        boletimMapper = new BoletimMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(boletimMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(boletimMapper.fromId(null)).isNull();
    }
}
