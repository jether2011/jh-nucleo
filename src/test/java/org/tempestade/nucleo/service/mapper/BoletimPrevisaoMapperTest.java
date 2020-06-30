package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BoletimPrevisaoMapperTest {

    private BoletimPrevisaoMapper boletimPrevisaoMapper;

    @BeforeEach
    public void setUp() {
        boletimPrevisaoMapper = new BoletimPrevisaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(boletimPrevisaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(boletimPrevisaoMapper.fromId(null)).isNull();
    }
}
