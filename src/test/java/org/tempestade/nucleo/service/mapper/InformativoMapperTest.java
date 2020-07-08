package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InformativoMapperTest {

    private InformativoMapper informativoMapper;

    @BeforeEach
    public void setUp() {
        informativoMapper = new InformativoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(informativoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(informativoMapper.fromId(null)).isNull();
    }
}
