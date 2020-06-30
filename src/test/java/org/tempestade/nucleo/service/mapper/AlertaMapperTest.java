package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlertaMapperTest {

    private AlertaMapper alertaMapper;

    @BeforeEach
    public void setUp() {
        alertaMapper = new AlertaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alertaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alertaMapper.fromId(null)).isNull();
    }
}
