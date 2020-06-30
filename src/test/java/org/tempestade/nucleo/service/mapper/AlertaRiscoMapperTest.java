package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlertaRiscoMapperTest {

    private AlertaRiscoMapper alertaRiscoMapper;

    @BeforeEach
    public void setUp() {
        alertaRiscoMapper = new AlertaRiscoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alertaRiscoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alertaRiscoMapper.fromId(null)).isNull();
    }
}
