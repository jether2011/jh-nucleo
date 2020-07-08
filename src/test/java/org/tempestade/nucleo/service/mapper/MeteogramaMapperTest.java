package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MeteogramaMapperTest {

    private MeteogramaMapper meteogramaMapper;

    @BeforeEach
    public void setUp() {
        meteogramaMapper = new MeteogramaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(meteogramaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(meteogramaMapper.fromId(null)).isNull();
    }
}
