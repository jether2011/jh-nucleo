package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ContatoTipoEnvioMapperTest {

    private ContatoTipoEnvioMapper contatoTipoEnvioMapper;

    @BeforeEach
    public void setUp() {
        contatoTipoEnvioMapper = new ContatoTipoEnvioMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(contatoTipoEnvioMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contatoTipoEnvioMapper.fromId(null)).isNull();
    }
}
