package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlvoBloqueioMapperTest {

    private AlvoBloqueioMapper alvoBloqueioMapper;

    @BeforeEach
    public void setUp() {
        alvoBloqueioMapper = new AlvoBloqueioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alvoBloqueioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alvoBloqueioMapper.fromId(null)).isNull();
    }
}
