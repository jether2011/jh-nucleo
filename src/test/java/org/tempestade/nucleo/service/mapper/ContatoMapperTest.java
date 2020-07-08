package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ContatoMapperTest {

    private ContatoMapper contatoMapper;

    @BeforeEach
    public void setUp() {
        contatoMapper = new ContatoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(contatoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contatoMapper.fromId(null)).isNull();
    }
}
