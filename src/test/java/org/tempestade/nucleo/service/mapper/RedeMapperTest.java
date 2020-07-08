package org.tempestade.nucleo.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RedeMapperTest {

    private RedeMapper redeMapper;

    @BeforeEach
    public void setUp() {
        redeMapper = new RedeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(redeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(redeMapper.fromId(null)).isNull();
    }
}
