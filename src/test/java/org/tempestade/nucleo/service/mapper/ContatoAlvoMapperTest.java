package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ContatoAlvoMapperTest {

    private ContatoAlvoMapper contatoAlvoMapper;

    @BeforeEach
    public void setUp() {
        contatoAlvoMapper = new ContatoAlvoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(contatoAlvoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contatoAlvoMapper.fromId(null)).isNull();
    }
}
