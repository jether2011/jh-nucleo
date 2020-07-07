package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlertaTipoMapperTest {

    private AlertaTipoMapper alertaTipoMapper;

    @BeforeEach
    public void setUp() {
        alertaTipoMapper = new AlertaTipoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alertaTipoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alertaTipoMapper.fromId(null)).isNull();
    }
}
