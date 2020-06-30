package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DescargaUnidadeMapperTest {

    private DescargaUnidadeMapper descargaUnidadeMapper;

    @BeforeEach
    public void setUp() {
        descargaUnidadeMapper = new DescargaUnidadeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(descargaUnidadeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(descargaUnidadeMapper.fromId(null)).isNull();
    }
}
