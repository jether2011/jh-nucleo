package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmpresaMapperTest {

    private EmpresaMapper empresaMapper;

    @BeforeEach
    public void setUp() {
        empresaMapper = new EmpresaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(empresaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(empresaMapper.fromId(null)).isNull();
    }
}
