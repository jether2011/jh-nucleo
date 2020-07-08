package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NoticiaMapperTest {

    private NoticiaMapper noticiaMapper;

    @BeforeEach
    public void setUp() {
        noticiaMapper = new NoticiaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(noticiaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(noticiaMapper.fromId(null)).isNull();
    }
}
