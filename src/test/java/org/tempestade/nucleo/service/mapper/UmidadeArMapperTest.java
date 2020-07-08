package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UmidadeArMapperTest {

    private UmidadeArMapper umidadeArMapper;

    @BeforeEach
    public void setUp() {
        umidadeArMapper = new UmidadeArMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(umidadeArMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(umidadeArMapper.fromId(null)).isNull();
    }
}
