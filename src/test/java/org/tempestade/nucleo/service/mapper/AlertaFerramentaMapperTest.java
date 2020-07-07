package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlertaFerramentaMapperTest {

    private AlertaFerramentaMapper alertaFerramentaMapper;

    @BeforeEach
    public void setUp() {
        alertaFerramentaMapper = new AlertaFerramentaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alertaFerramentaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alertaFerramentaMapper.fromId(null)).isNull();
    }
}
