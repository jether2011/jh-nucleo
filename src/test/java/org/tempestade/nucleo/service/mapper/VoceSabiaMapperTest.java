package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VoceSabiaMapperTest {

    private VoceSabiaMapper voceSabiaMapper;

    @BeforeEach
    public void setUp() {
        voceSabiaMapper = new VoceSabiaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(voceSabiaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(voceSabiaMapper.fromId(null)).isNull();
    }
}
