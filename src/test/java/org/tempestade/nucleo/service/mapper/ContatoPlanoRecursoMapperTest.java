package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ContatoPlanoRecursoMapperTest {

    private ContatoPlanoRecursoMapper contatoPlanoRecursoMapper;

    @BeforeEach
    public void setUp() {
        contatoPlanoRecursoMapper = new ContatoPlanoRecursoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(contatoPlanoRecursoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contatoPlanoRecursoMapper.fromId(null)).isNull();
    }
}
