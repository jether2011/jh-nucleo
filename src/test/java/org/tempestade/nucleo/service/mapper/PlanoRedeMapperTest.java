package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanoRedeMapperTest {

    private PlanoRedeMapper planoRedeMapper;

    @BeforeEach
    public void setUp() {
        planoRedeMapper = new PlanoRedeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planoRedeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planoRedeMapper.fromId(null)).isNull();
    }
}
