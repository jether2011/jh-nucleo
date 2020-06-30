package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlvoMapperTest {

    private AlvoMapper alvoMapper;

    @BeforeEach
    public void setUp() {
        alvoMapper = new AlvoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alvoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alvoMapper.fromId(null)).isNull();
    }
}
