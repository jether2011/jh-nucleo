package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsolidacaoMapperTest {

    private ConsolidacaoMapper consolidacaoMapper;

    @BeforeEach
    public void setUp() {
        consolidacaoMapper = new ConsolidacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(consolidacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(consolidacaoMapper.fromId(null)).isNull();
    }
}
